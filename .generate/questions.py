"""
questions.py

Module for pulling down, caching, and looking up question data easily.
"""
from dataclasses import dataclass
import json
from pathlib import Path
import time
from typing import Dict, List
from logging import getLogger
from pydantic import BaseModel
from requests import Session

logger = getLogger(__name__)

query = """

"""


class Tag(BaseModel):
    """
    A class representing a tag.
    """

    slug: str
    name: str


class Question(BaseModel):
    """
    A class representing a question.
    """

    id: int
    slug: str
    title: str
    difficulty: str
    tags: List[Tag]

    @property
    def url(self) -> str:
        """
        Gets the URL to the question.
        """
        return f"https://leetcode.com/problems/{self.slug}"


class QuestionDatabase(object):
    """
    A class for managing the question database.
    """

    def __init__(self, cache_path: Path, cache_time: int, debug: bool = False) -> None:
        """
        Initializes the question database.

        :param cache_time: The amount of time to cache all questions for, in seconds.
        """
        self.debug = debug
        self.client = Session()
        self.client.headers["Content-Type"] = "application/json"
        self.client.headers["Referer"] = "https://leetcode.com/problemset/all/"
        self.client.headers["User-Agent"] = "lc-track/0.0.1"
        self.client.headers["Accept"] = "application/json"

        # Cache path exists
        self.questions = None  # The data we're extracting from the cache
        if cache_path.exists():
            if not cache_path.is_file():
                raise ValueError(f"Cache path {cache_path} is not a file.")

            with cache_path.open("r") as cache_file:
                raw_cache = json.load(cache_file)

            cache_elapsed = time.time() - raw_cache["fetch_time"]
            if cache_elapsed < cache_time:
                self.questions = [
                    Question(**question) for question in raw_cache["questions"]
                ]
                logger.info(f"Loaded {len(self.questions):,} questions from cache.")
        else:
            logger.info(f"Cache file {cache_path} does not exist.")

        # Question data wasn't loaded from the cache, so we fetch it (and store it in the cache) now
        if self.questions is None:

            # Make sure the directory holding it does exist (this shouldn't ever be raised, so this is just a sanity check)
            if not cache_path.parent.exists():
                raise RuntimeError(
                    f"Directory where cache would exist ({cache_path.parent}) does not exist."
                )

            logger.info("Fetching questions from LeetCode...")
            fetch_time = int(time.time())
            self.questions = self.fetch()

            logger.info(f"Writing {len(self.questions)} questions to cache...")
            with cache_path.open("w") as cache_file:
                json.dump(
                    {
                        "fetch_time": fetch_time,
                        "questions": [
                            question.model_dump() for question in self.questions
                        ],
                    },
                    cache_file,
                )

        self.by_id: Dict[int, Question] = {
            question.id: question for question in self.questions
        }
        self.by_slug: Dict[str, Question] = {
            question.slug: question for question in self.questions
        }

    def fetch(self) -> List[Question]:
        logger.debug("Fetching total number of questions...")
        totalQuestionsQuery = """
        query getTotalQuestions($categorySlug: String, $filters: QuestionListFilterInput) {
            questions: questionList(
                categorySlug: $categorySlug
                limit: 1,
                filters: $filters
                skip: 0
            ) {
                total: totalNum
            }
        }"""
        total_questions_response = self.client.get(
            "https://leetcode.com/graphql",
            json={
                "query": totalQuestionsQuery,
                "variables": {"categorySlug": "", "filters": {}},
            },
        )

        if not total_questions_response.ok:
            raise RuntimeError(
                f"Failed to fetch total number of questions: {total_questions_response.status_code} {total_questions_response.reason}"
            )

        total: int = total_questions_response.json()["data"]["questions"]["total"]
        logger.debug(f"Total number of questions: {total:,}")

        logger.debug("Fetching questions...")
        questionsQuery = """
        query questionsList($categorySlug: String, $limit: Int, $filters: QuestionListFilterInput) {
            questionsList: questionList(
                categorySlug: $categorySlug
                limit: $limit
                filters: $filters
            ) {
                total: totalNum
                questions: data {
                    difficulty
                    title
                    titleSlug
                    id: questionFrontendId
                    topicTags {
                        name
                        slug
                    }
                }
            }
        }"""
        questions_response = self.client.get(
            "https://leetcode.com/graphql",
            json={
                "query": questionsQuery,
                "variables": {
                    "categorySlug": "",
                    "filters": {},
                    "limit": 50 if self.debug else total,
                },
            },
        )

        logger.debug("{:,} bytes received.".format(len(questions_response.content)))

        if not questions_response.ok:
            raise RuntimeError(
                f"Failed to fetch questions: {questions_response.status_code} {questions_response.reason}"
            )

        # Parse & extract question data into Pydantic models
        questions = questions_response.json()["data"]["questionsList"]["questions"]
        return [
            Question(
                id=question["id"],
                slug=question["titleSlug"],
                title=question["title"],
                difficulty=question["difficulty"],
                tags=[
                    Tag(name=tag["name"], slug=tag["slug"])
                    for tag in question["topicTags"]
                ],
            )
            for question in questions
        ]

    def get_by_id(self, question_id: int) -> Question | None:
        """
        Gets a question by its ID.
        """
        return self.by_id.get(question_id)

    def get_by_slug(self, slug: str) -> Question | None:
        """
        Gets a question by its slug.
        """
        return self.by_slug.get(slug)
