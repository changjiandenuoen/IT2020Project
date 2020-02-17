package online.healthCheck;


import com.codahale.metrics.health.HealthCheck;

import shared.Model_Database;


public class DatabaseHealthCheck extends HealthCheck {
	
	private final Model_Database database;

    public DatabaseHealthCheck(Model_Database database) {
        this.database = database;
    }

    @Override
    protected Result check() throws Exception {
    	
        if (database.checkConnection()) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Cannot connect to database.");
        }
    }
}
