The `PlanRestController` class is a REST controller that handles HTTP requests related to plans. Let's go through the code and understand it in detail:

1. Import Statements:
The class imports necessary classes and dependencies.

2. Class and Dependency Injection:
```java
@RestController
public class PlanRestController {
    
    private PlanService planService;
    private Map<String, String> messages;

    public PlanRestController(PlanService planService, AppProperties appProps) {
        this.planService = planService;
        this.messages = appProps.getMessages();
        System.out.println(this.messages);
    }
    
    // Rest of the code
}
```
The class is annotated with `@RestController` to indicate that it's a REST controller. The class has two private fields: `planService` of type `PlanService` and `messages` of type `Map<String, String>`. These dependencies are injected through the constructor using dependency injection. The `AppProperties` dependency is used to retrieve messages from the application properties file.

3. Mapping HTTP Endpoints:
The class defines various methods annotated with HTTP method annotations (`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`) to map specific HTTP endpoints.

3.1. `planCategories()`:
```java
@GetMapping("/categories")
public ResponseEntity<Map<Integer, String>> planCategories() {
    Map<Integer, String> categories = planService.getPlanCategories();
    return new ResponseEntity<>(categories, HttpStatus.OK);
}
```
This method handles the GET request for the "/categories" endpoint. It invokes the `planService.getPlanCategories()` method to retrieve the plan categories and returns them in the response body as a `Map<Integer, String>` with the HTTP status code `HttpStatus.OK`.

3.2. `savePlan()`:
```java
@PostMapping("/plan")
public ResponseEntity<String> savePlan(@RequestBody Plan plan) {
    String responseMsg = AppConstants.EMPTY_STR;
    boolean isSaved = planService.savePlan(plan);
    
    if (isSaved) {
        responseMsg = messages.get("planSaveSucc");
    } else {
        responseMsg = messages.get(AppConstants.PLAN_SAVE_FAIL);
    }
    
    return new ResponseEntity<>(responseMsg, HttpStatus.CREATED);
}
```
This method handles the POST request for the "/plan" endpoint. It receives a `Plan` object in the request body using the `@RequestBody` annotation. The method invokes the `planService.savePlan(plan)` method to save the plan and generates a response message based on the saving result. The response message is returned with the HTTP status code `HttpStatus.CREATED`.

3.3. `plans()`:
```java
@GetMapping("/plans")
public ResponseEntity<List<Plan>> plans() {
    List<Plan> allPlans = planService.getAllPlans();
    return new ResponseEntity<>(allPlans, HttpStatus.OK);
}
```
This method handles the GET request for the "/plans" endpoint. It invokes the `planService.getAllPlans()` method to retrieve all plans and returns them in the response body as a list of plans (`List<Plan>`) with the HTTP status code `HttpStatus.OK`.

3.4. `editPlan()`:
```java
@GetMapping("plan/{planId}")
public ResponseEntity<Plan> editPlan(@PathVariable Integer planId) {
    Plan plan = planService.getPlanById(planId);
    return new ResponseEntity<>(plan, HttpStatus.OK);
}
```
This method handles the GET request for the "/plan/{planId}" endpoint, where "{planId}" is a path variable representing the ID of the plan to be edited. It invokes the `planService.getPlanById(planId)` method to retrieve the plan with the given ID and returns it in the response body as a `Plan` object with the HTTP status code

 `HttpStatus.OK`.

3.5. `deletePlan()`:
```java
@DeleteMapping("plan/{planId}")
public ResponseEntity<Plan> deletePlan(@PathVariable Integer planId) {
    Plan plan = planService.getPlanById(planId);
    boolean isDeleted = planService.deletePlanById(planId);
    String msg = AppConstants.EMPTY_STR;

    if (isDeleted) {
        msg = messages.get(AppConstants.PLAN_DELETE_SUCC);
    } else {
        msg = messages.get(AppConstants.PLAN_SAVE_FAIL);
    }

    return new ResponseEntity<>(plan, HttpStatus.OK);
}
```
This method handles the DELETE request for the "/plan/{planId}" endpoint, where "{planId}" is a path variable representing the ID of the plan to be deleted. It invokes the `planService.getPlanById(planId)` method to retrieve the plan with the given ID. Then, it invokes the `planService.deletePlanById(planId)` method to delete the plan and generates a response message based on the deletion result. The plan and the response message are returned in the response body with the HTTP status code `HttpStatus.OK`.

3.6. `updatePlan()`:
```java
@PutMapping("/plan")
public ResponseEntity<String> updatePlan(@RequestBody Plan plan) {
    boolean isUpdated = planService.updatePlan(plan);
    String msg = AppConstants.EMPTY_STR;

    if (isUpdated) {
        msg = messages.get(AppConstants.PLAN_UPDATE_SUCC);
    } else {
        msg = messages.get(AppConstants.PLAN_UPDATE_FAIL);
    }

    return new ResponseEntity<>(msg, HttpStatus.OK);
}
```
This method handles the PUT request for the "/plan" endpoint. It receives a `Plan` object in the request body using the `@RequestBody` annotation. The method invokes the `planService.updatePlan(plan)` method to update the plan and generates a response message based on the update result. The response message is returned with the HTTP status code `HttpStatus.OK`.

3.7. `statusChange()`:
```java
@PutMapping("/status-change/{planId}/{status}")
public ResponseEntity<String> statusChange(@PathVariable Integer planId, @PathVariable String status) {
    String msg = AppConstants.EMPTY_STR;
    boolean isStatusChanged = planService.planStatusChange(planId, status);

    if (isStatusChanged) {
        msg = messages.get(AppConstants.PLAN_STATUS_CHANGE);
    } else {
        msg = messages.get(AppConstants.PLAN_STATUS_CHANGE_FAIL);
    }

    return new ResponseEntity<>(msg, HttpStatus.OK);
}
```
This method handles the PUT request for the "/status-change/{planId}/{status}" endpoint, where "{planId}" and "{status}" are path variables representing the ID of the plan and the new status, respectively. It invokes the `planService.planStatusChange(planId, status)` method to change the status of the plan and generates a response message based on the status change result. The response message is returned with the HTTP status code `HttpStatus.OK`.

That's a detailed explanation of the `PlanRestController` class and its methods.