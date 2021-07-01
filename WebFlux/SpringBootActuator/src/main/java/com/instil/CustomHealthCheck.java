package com.instil;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("customHealth")
public class CustomHealthCheck extends AbstractHealthIndicator {
	@Override
	protected void doHealthCheck(Health.Builder builder) {
		Map<String, Integer> details = new HashMap<>();
		details.put("foo", 123);
		details.put("bar", 456);
		details.put("zed", 789);

		builder.up().withDetails(details);
	}
}
