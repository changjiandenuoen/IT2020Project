package online.healthCheck;


import com.codahale.metrics.health.HealthCheck;

import com.fasterxml.jackson.databind.ObjectMapper;

import shared.Model;


public class ModelHealthCheck extends HealthCheck {
	
	private final Model model;

    public ModelHealthCheck(Model model) {
        this.model = model;
    }

    @Override
    protected Result check() throws Exception {
    	
    	ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(model);
    	
        String[] containList = { "gameStatus", "round", "currAttributeIndex", "hostIndex", "winnerIndex", "winningCard", "numPlayers" };
        String notContain = "";
        for(int i = 0; i < containList.length; i++) {
        	if(!jsonString.contains(containList[i])) {
        		notContain += " " + containList[i];
        	}
        }
        
        if (!notContain.isBlank()) {
            return Result.healthy();
        }
        return Result.unhealthy("Model doesn't contain " + notContain);
    }

}
