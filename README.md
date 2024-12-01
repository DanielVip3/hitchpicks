# Hitchpicks

Hitchpicks is a web app to track your watched movies and TV shows.

This README is W.I.P.!

## Commit structure

Commit structure to follow:  
"**\<emoji\>** - meaningful title"  
"brief description of changes  
..."

Emojis are roughly based on [Gitmoji](https://gitmoji.dev/).  
🎉 - Initial commit  
✨ - New feature  
🐛 - Bug fix  
📝 - Documentation  
🧪 - Test  
🗃️ - Database  
💄 - UI and styles  
♻️ - Refactoring  
💚 - CI

## How to install

To install PostgreSQL and pgAdmin: [https://www.postgresql.org/download/windows/](https://www.postgresql.org/download/windows/).  
Remember to create a user called `hitchpicks` with password `admin` and a database called `hitchpicks`.

To install frontend packages:
```bash
cd /src/main/frontend
npm install
```

Then, run Maven lifecycle `install`.

Remember to install IntelliJ's Tailwind and JTE plugins.

## How to run

First, always
```bash
git pull origin main
```

Then
```bash
cd /src/main/frontend
npm run watch
```

Last, you can execute the application from IntelliJ.

## How to run CheckStyle and tests

For Checkstyle, use Maven's menu in IntelliJ:
hitchpicks > Plugins > checkstyle > checkstyle:check

For tests, use Maven's menu in IntelliJ:
hitchpicks > Lifecycle > test

## Useful documentations

https://tailwindcss.com/  
https://jte.gg/