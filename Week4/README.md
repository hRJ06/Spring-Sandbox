# JPA Lifecycle Callback Annotations

| Annotation    | Trigger Time                           | Common Use Case                            |
|---------------|----------------------------------------|--------------------------------------------|
| `@PrePersist` | Before entity is persisted (inserted)  | Initialize default values or audit fields  |
| `@PostPersist`| After entity is persisted (inserted)   | Logging, post-save operations              |
| `@PreUpdate`  | Before entity is updated               | Modify fields like `updatedAt`, validation |
| `@PostUpdate` | After entity is updated                | Logging, audit changes                    |
| `@PreRemove`  | Before entity is removed (deleted)     | Soft deletes, prevent certain deletions    |
| `@PostRemove` | After entity is removed (deleted)      | Cleanup or logging                        |
| `@PostLoad`   | After entity is loaded from the database| Initialize transient fields or logging    |

# Hibernate Envers Revision Types

| `REVTYPE` | Operation | Description                              |
|-----------|-----------|------------------------------------------|
| `0`       | ADD       | Entity was added (inserted)              |
| `1`       | MOD       | Entity was modified (updated)            |
| `2`       | DEL       | Entity was deleted (removed from DB)     |
