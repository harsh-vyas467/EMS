
✅ Tech Stack:

Spring Boot – Backend Framework

PostgreSQL – Relational Database

Flyway – Database Migration Tool


---

🚀 Why Use Flyway?

In real-world production systems, database changes should never be managed directly by the application. Here's why:

⚠️ Risk of Data Loss: Misconfigured properties like spring.jpa.hibernate.ddl-auto=create/drop can wipe the entire database if applied accidentally.

🔄 Coupling with Application Lifecycle: If data is inserted/updated through application logic at startup, every schema/data change requires a redeploy.

🛠️ Manual Changes are Error-Prone: Developers or DB admins manually altering schemas can introduce inconsistencies across environments.


---

🛡️ The Solution — Flyway

Flyway solves these problems by offering:

✅ Version-controlled schema migrations

✅ Safe, repeatable, and auditable database changes

✅ Environment consistency across development, staging, and production

✅ Decoupled database management — no need to redeploy the app for DB updates


---

📌 
> Don’t rely on your app to manage production databases.
Use Flyway to ensure database changes are safe, trackable, and independent of your application code.


-------
add this lines in application.properties file to auto generate schema, and later you can put that file inside db.migration and rename it.
#To create database schema through JPA

#spring.jpa.properties.jakarta.persistence.schema-generation.scripts.action=create
#spring.jpa.properties.jakarta.persistence.schema-generation.scripts.create-target=schema.sql
#spring.jpa.properties.jakarta.persistence.schema-generation.scripts.create-source=metadata


----------------
Flyway has a specific naming convention for migration files, structured as V1__.sql. This helps manage database changes efficiently.
Additionally, Flyway maintains a history of these migrations, storing checksums for each script. Every time you run your application, Flyway verifies these checksums, ensuring that existing SQL scripts remain unchanged.
If you need to make any modifications, you'll have to create a new migration script instead of altering the existing one.
This approach promotes better version control and helps prevent accidental changes that could disrupt your database schema.

