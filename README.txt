# Student Management System UI

## Run
1. Extract the ZIP file.
2. Open the `student-management-ui` folder.
3. Double-click `index.html`, or open the folder in VS Code and use Live Server.

## Features
- Add, view, search, update, and delete students
- Fields: roll number, name, address, email, phone number, course, marks
- Export records to `students.csv`
- Browser `localStorage` persistence
- Responsive layout, animated cards, navigation and toast messages

## Note
This is a frontend-only version. It stores records in the browser's localStorage and does not connect to your Java ArrayList or a Spring Boot backend. Phone number is stored as text so leading zeros and `+` are preserved. Marks are validated from 0 to 100.
