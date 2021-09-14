package net.badbird5907.manhuntcore.api.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.badbird5907.blib.event.SimpleEvent;
import net.badbird5907.manhuntcore.scenario.Scenario;
import net.badbird5907.manhuntcore.scenario.ScenarioInfo;

@RequiredArgsConstructor
@Getter
public class PostScenarioLoadEvent extends SimpleEvent {
    private final Scenario scenario;
    private final ScenarioInfo scenarioInfo;
}
