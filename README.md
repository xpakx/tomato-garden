# Tomato Garden

## General info
A simple web pomodoro timer written in Java + Spring (backend) and Typescript + Angular (frontend).

![gif](readme_files/screen.gif)

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)

## Technologies
Project is created with:
* Java
* Spring Boot
* Typescript
* Angular
* PostgreSQL

## Setup
In order to run project locally you need to clone this repository and build project with Docker Compose:

```
$ git clone https://github.com/xpakx/tomato-garden.git
$ cd tomato-garden
$ docker-compose up --build -d
```

To stop:
```
$ docker-compose stop
```

## Features
- [x] Timer
	- [x] Pomodoro
	- [x] GUI
	- [x] Breaks
- [x] Tags
	- [x] Adding
	- [x] Deleting
	- [x] Tagging
- [x] Authentication with JWT
- [x] Saving timer settings
- [x] Statistics
	- [x] General statistics
	- [x] Timeline


