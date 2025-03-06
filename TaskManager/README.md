# Task Manager CLI - Conceptual Design Outline

##  Core Functionalities
* Task CRUD Operations: Create, View, Update, Delete tasks.
* Task Completion: Mark tasks as completed.
* Task Categorization: Work-related vs. Personal tasks.
* Sorting & Filtering: Sort tasks dynamically based on different criteria.
* Persistent Storage: Save and retrieve tasks from a file or database.

## Object-Oriented Structure

* Task Manager → Centralized system to handle task operations.
* Task → Represents an individual task with essential details.
* Task Categories → Work tasks and personal tasks extend the base task.
* Task Storage → Responsible for saving and retrieving tasks.
* Task Sorting Mechanism → Dynamically sorts tasks using different strategies.

## Attributes of Each Component

### Task (Base Class)
* Identity → Unique identifier for each task.
* Description → Title and details of the task.
* Due Date → Optional deadline for completion.
* Status → Indicates if the task is completed or pending.
### Task Categories (Subclasses)
* Work Task → May include an associated project name.
* Personal Task → May include a location or event reference.
### Task Manager (Singleton)
* Task Collection → Holds and manages all tasks.
* Task Operations → Methods for adding, retrieving, and modifying tasks.
* Task Count Tracking → Provides method to retrieve the total number of tasks.
* Task Retrieval → Implements method to fetch a task by its unique identifier. 
### Task Sorting (Strategy Pattern)
* Sort By Due Date → Orders tasks based on deadlines.
* Sort By Title → Alphabetically arranges tasks.
### User Interaction (CLI)
* Menu System → Provides user options for task management.
* Input Handling → Accepts and processes user commands.
* Feedback Mechanism → Displays task details and operation results.

## Design Patterns Used
* Singleton → Ensures only one Task Manager instance exists.
* Strategy → Allows dynamic sorting of tasks.
* Factory (Optional) → Simplifies task creation based on category.