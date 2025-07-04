
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

------------------------------------------------------------------------------------------

🔧 Aspect-Oriented Programming (AOP) — Spring Boot Notes
✅ What is AOP?
AOP is used to separate common logic (like logging, security, transactions) from your main business code.
This logic is called a cross-cutting concern.

🧠 Why Use AOP?
Avoid repeating code in multiple classes (e.g. logging in every service)

Clean, modular, and maintainable

Handles things "around" your main logic

🛠️ AOP Terms 
Term	Meaning
Aspect	A class that contains cross-cutting logic
Advice	The action to take (e.g., log, check security)
JoinPoint	The actual place where the method runs
Pointcut	The condition (expression) to match the method(s)
Weaving	The process of applying aspects to the target methods

🧩 Common AOP Annotations
Annotation	When it runs	Used for
@Aspect	Declares an aspect class	Needed for AOP class
@Before	Before the method runs	Log input, check security
@AfterReturning	After method returns (success)	Log output
@AfterThrowing	If method throws exception	Log or handle error
@After	After method (success/fail)	Cleanup (not for exceptions)
@Around	Wraps method (entry/exit)	Full control, logs everything

✅ Example: Logging with @Around

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.practice.springboot.ems..*(..))")
    public Object logAll(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("➡️ Entering: {}", joinPoint.getSignature());
        try {
            Object result = joinPoint.proceed();
            log.info("✅ Returned: {}", result);
            return result;
        } catch (Throwable ex) {
            log.error("❌ Exception: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
🔐 AOP for Security (Simulated)

@Before("execution(* com.practice.springboot.ems.controller..*(..))")
public void checkSecurity(JoinPoint joinPoint) {
log.info("✅ Security check passed: {}", joinPoint.getSignature());
// throw new AccessDeniedException(...) to block
}
For real security, use Spring Security: @PreAuthorize("hasRole('ADMIN')")

📦 Maven Dependency for AOP

<!-- Add to pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>


✅ What You Can Do with AOP
📜 Logging (input, output, exceptions)

🔒 Security checks

⏱️ Execution time tracking

🔁 Transactions

🔍 Validation or sanitization

✅ Best Practices
Use AOP for common concerns, not core logic

Avoid logging sensitive data

Prefer @Around if you want full control

--------------------------------------------------------------------------

🔁 @Transactional in Spring Boot
✅ What is @Transactional?
It tells Spring to wrap a method in a database transaction.
If anything goes wrong (like an exception), it automatically rolls back changes.

🔧 When to Use It
On service methods that perform database write operations

On methods that do multiple DB operations and must succeed together

🛠️ Example
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto updatedData) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        emp.setFirstName(updatedData.getFirstName());
        emp.setEmail(updatedData.getEmail());

        return EmployeeMapper.mapToEmployeeDto(employeeRepository.save(emp));
    }
}
✅ If no error → changes are committed
❌ If error occurs → all changes are rolled back automatically

🧠 Where to Put @Transactional
Location	Recommended?	Why?
@Service class	✅ Yes	Most reliable; logic layer
@Repository class	❌ Not needed	Spring Data already manages it
@Controller	❌ No	Controllers should not manage transactions

🔁 How It Works
Uses Spring AOP under the hood

Wraps method in a proxy that manages the transaction

⚠️ Common Mistakes
Mistake	Problem
Calling a @Transactional method from the same class	Proxy won't work — transaction is ignored
Putting @Transactional on private methods	Spring can’t proxy them — won't apply
Not handling exceptions properly	Some exceptions (like checked) may not trigger rollback

🔧 Rollback for Custom Exceptions
By default, only unchecked exceptions trigger rollback.
To rollback on custom exceptions, do:


@Transactional(rollbackFor = YourCustomException.class)
🧾 Optional Config

@Transactional(
propagation = Propagation.REQUIRED,
isolation = Isolation.READ_COMMITTED,
rollbackFor = Exception.class
)
These give more control over transaction behavior (e.g., nesting, locking)

✅ Summary
Feature	Explanation
Automatic rollback	On runtime exceptions
Where to use	@Service methods with DB operations
Works with	Spring AOP (proxy-based)
Avoid on	Controllers, internal method calls

