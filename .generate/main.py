from datetime import datetime
from pathlib import Path
from jinja2 import Template
import logging
import json

"""
This script is used to generate the README.md file for the repository.
As solutions are created, amended, and improved over time, this script will assist
in the generation of a in-depth, well-linked, and detailed README.md file for the repository.
- In-progress solutions can be excluded, or marked as such using specific tags in the file.
- JSON files at the root of each solution folder provide information about the solution to be used by this script.
- Can support multiple languages and/or multiple solutions per problem.
- Problems can be given special categories to help divide them up into more manageable/digestable sections for viewers.
    - Categories are fetched directly from LeetCode's API - they should not be considered static over time.
    - Multiple categories can be assigned to a problem.
"""

SCRIPT_PATH: Path = Path(__file__).absolute()
SCRIPTS_DIRECTORY: Path = SCRIPT_PATH.parent
ROOT_DIRECTORY: Path = SCRIPTS_DIRECTORY.parent

TEMPLATE_PATH: Path = SCRIPTS_DIRECTORY / "TEMPLATE.j2"
SOLUTIONS_DIRECTORY: Path = SCRIPTS_DIRECTORY.parent / "solutions"

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


def is_solution_ready(solution_directory: Path) -> bool:
    """
    Determines if a solution is ready to be included in the README.md file.
    This is determined by the presence of a `meta.json` file in the solution directory.
    If the meta.json file is improperly formatted, missing key attributes, or contains invalid values, this will return False.
    """

    if not solution_directory.is_dir():
        return False
    elif not (solution_directory / "meta.json").exists():
        return False

    return True


def generate_row(solution_directory: Path) -> str:
    """
    Returns a dictionary containing information about the solution.
    The dictionary is created by reading the `meta.json` file in the solution directory.
    """

    with (solution_directory / "meta.json").open("r") as meta_file:
        meta = json.load(meta_file)

    solutions = " ".join(
        f"[{solution['name']}](/{(solution_directory / solution['path']).relative_to(ROOT_DIRECTORY)})"
        for solution in meta["solutions"]
    )

    columns = [
        123,
        meta["title"],
        solutions,
        "kinda hard idk",
    ]

    return "| " + " | ".join(map(str, columns)) + " |"


def main():
    # TODO: Prevent inclusion of a solution if a linked source file includes a "EXCLUDE" tag.
    # TODO: Include, but mark as "in-progress" if a linked source file includes a "WIP" or "WORK IN PROGRESS" tag (case insensitive).
    # TODO: Generate the README.md file
    from questions import QuestionDatabase

    database = QuestionDatabase(
        cache_path=ROOT_DIRECTORY / "questions.json", cache_time=60 * 60 * 24
    )

    table = [
        "| # | Title | Solution | Difficulty |",
        "|-|-|-|-|",
    ]

    table.extend(
        map(generate_row, filter(is_solution_ready, SOLUTIONS_DIRECTORY.glob("*")))
    )
    table = "\n".join(table)

    with TEMPLATE_PATH.open("r") as template_file:
        template = Template(template_file.read())

    with (ROOT_DIRECTORY / "README.md").open("w") as readme_file:
        readme_file.write(
            template.render(date=datetime.now().strftime("%Y-%m-%d"), table=table)
        )


if __name__ == "__main__":
    main()