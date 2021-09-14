package net.badbird5907.manhuntcore.manager.impl;

import lombok.SneakyThrows;
import net.badbird5907.blib.objects.maps.pair.HashPairMap;
import net.badbird5907.blib.objects.maps.pair.PairMap;
import net.badbird5907.blib.objects.tuple.Pair;
import net.badbird5907.blib.util.Logger;
import net.badbird5907.blib.util.ReflectionUtils;
import net.badbird5907.manhuntcore.ManhuntCore;
import net.badbird5907.manhuntcore.api.events.PostScenarioLoadEvent;
import net.badbird5907.manhuntcore.manager.Manager;
import net.badbird5907.manhuntcore.object.ScenarioLoadResult;
import net.badbird5907.manhuntcore.scenario.Scenario;
import net.badbird5907.manhuntcore.scenario.ScenarioInfo;
import org.bukkit.Bukkit;

public class ScenarioManager extends Manager {
    private PairMap<String,ScenarioInfo,Scenario> scenarios = new HashPairMap<>();
    private PairMap<String,ScenarioInfo,Scenario> activeScenarios = new HashPairMap<>();
    @SneakyThrows
    @Override
    public void init(ManhuntCore plugin) {
        for (Class<?> aClass : ReflectionUtils.getClassesInPackage(plugin, "net.badbird5907.manhuntcore.scenario.impl")) {
            if (aClass.isAnnotationPresent(ScenarioInfo.class) && aClass.isAssignableFrom(Scenario.class)){
                loadScenario(aClass);
            }
        }
        Logger.info("Loaded (&b%1&r) scenarios.",scenarios.size());
    }
    @SneakyThrows
    public void loadScenario(Class<?> clazz){
        ScenarioInfo scenarioInfo = clazz.getAnnotation(ScenarioInfo.class);
        Scenario scenario = (Scenario) clazz.newInstance();
        scenario.load(ManhuntCore.getInstance());
        scenarios.put(scenarioInfo.name().toLowerCase(),scenarioInfo,scenario);
    }
    public ScenarioLoadResult enableScenario(String scenName){
        if (activeScenarios.containsKey(scenName.toLowerCase())){
            return ScenarioLoadResult.ALREADY_LOADED;
        }
        Pair<ScenarioInfo, Scenario> pair = scenarios.get(scenName.toLowerCase());
        Scenario scenario = pair.getValue1();
        activeScenarios.put(pair.getValue0().name().toLowerCase(),pair.getValue0(),scenario);
        Bukkit.getServer().getPluginManager().registerEvents(scenario,ManhuntCore.getInstance());
        new PostScenarioLoadEvent(scenario, scenario.getScenarioInfo()).call();
        return ScenarioLoadResult.SUCCESS;
    }
    @Override
    public void disable(ManhuntCore plugin) {

    }
}
