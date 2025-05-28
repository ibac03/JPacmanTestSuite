# JPacman Comprehensive test suite


A comprehensive test suite for the JPacman game implementation in Java, originally developed under GitLab and now hosted on GitHub.

## Features
- **Unit & Parameterized Tests** (JUnit 5) and **Property-Based Tests** (Jqwik) for pellet collisions, ghost interactions, and player state  
- **Integration Tests** with Mockito to isolate `MapParser` and simulate custom map scenarios for ghost AI  
- **Structural Testing** in IntelliJ achieving 100% branch coverage on `Game.start()`  
- **Manual Exploratory & Acceptance Testing** (e.g. “suspend game” scenario) to ensure feature completeness  

## Prerequisites
- Java 11 or newer  
- Gradle (or Maven)  
- Git  

## Getting Started

1. **Clone the repo**  
   ```bash
   git clone https://github.com/ibac03/JPacmanTestSuite.git
   cd JPacmanTestSuite
