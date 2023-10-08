"""
questions.py

Module for pulling down, caching, and looking up question data easily.
"""
from dataclasses import dataclass
import json
from pathlib import Path
import time
from typing import List
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
    body: str
    tags: List[Tag]


class QuestionDatabase(object):
    """
    A class for managing the question database.
    """

    def __init__(self, cache_path: Path, cache_time: int) -> None:
        """
        Initializes the question database.

        :param cache_time: The amount of time to cache all questions for, in seconds.
        """
        self.client = Session()
        self.client.headers["Content-Type"] = "application/json"
        self.client.headers["Referer"] = "https://leetcode.com/problemset/all/"
        self.client.headers["User-Agent"] = "lc-track/0.0.1"
        self.client.headers["Accept"] = "application/json"

        # Cache path exists
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
                logger.info(f"Loaded {len(self.questions)} questions from cache.")
                return

        # Make sure the directory holding it does exist (this shouldn't ever be raised, so this is just a sanity check)
        if not cache_path.parent.exists():
            raise RuntimeError(
                f"Directory where cache would exist ({cache_path.parent}) does not exist."
            )

        # Fetch cache questions
        logger.info("Fetching questions from LeetCode...")

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
        query questionsList($categorySlug: String, $limit: Int, $skip: Int, $filters: QuestionListFilterInput) {
            problemsetQuestionList: questionList(
                categorySlug: $categorySlug
                limit: $limit
                skip: $skip
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
        }
        """
        questions_response = self.client.get(
            "https://leetcode.com/graphql",
            json={
                "query": questionsQuery,
                "variables": {
                    "categorySlug": "",
                    "filters": {},
                    "limit": 1,
                    "skip": 0,
                },
            },
        )

        print(questions_response.json())

    def get_by_id(self, question_id: int) -> Question:
        """
        Gets a question by its ID.
        """

        raise NotImplementedError

    def get_by_slug(self, slug: str) -> Question:
        """
        Gets a question by its slug.
        """

        raise NotImplementedError
