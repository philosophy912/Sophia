package com.chinatsp.automation;

import com.chinatsp.automation.api.builder.TestCaseTypeEnum;
import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.compare.Function;
import com.chinatsp.automation.entity.testcase.Cluster;
import com.chinatsp.automation.entity.testcase.Receive;
import com.chinatsp.automation.entity.testcase.Send;
import com.chinatsp.automation.impl.builder.CodeBuilder;
import com.chinatsp.automation.impl.reader.CanActionsReader;
import com.chinatsp.automation.impl.reader.ClusterReader;
import com.chinatsp.automation.impl.reader.FunctionReader;
import com.chinatsp.automation.impl.reader.ReceiverReader;
import com.chinatsp.automation.impl.reader.ScreenActionsReader;
import com.chinatsp.automation.impl.reader.SenderReader;
import com.chinatsp.automation.impl.utils.ReaderUtils;
import com.philosophy.base.util.FilesUtils;
import com.philosophy.excel.utils.ExcelUtils;
import com.philosophy.txt.util.TxtUtils;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.chinatsp.automation.api.IConstant.ACTION;
import static com.chinatsp.automation.api.IConstant.CONDITION;

class AutomationApplicationTests {
/*
    @SneakyThrows
    public static void test1() {
        String folder = "D:\\Workspace\\github\\knife\\automation\\src\\test\\resources";
        Path path = Paths.get(folder + File.separator + "1Q1空调屏测试用例汇总_V1.xlsx");
        ExcelUtils excelUtils = new ExcelUtils();
        ReaderUtils readerUtils = new ReaderUtils();
        Workbook workbook = excelUtils.openWorkbook(path);
        Sheet sheet;

        FunctionReader functionReader = new FunctionReader();
        sheet = readerUtils.getSpecificSheet(workbook, "actions");
        List<Function> functions = functionReader.read(sheet);
        System.out.println("done functions size = " + functions.size());


        ReceiverReader airConditionReceiverReader = new ReceiverReader();
        sheet = readerUtils.getSpecificSheet(workbook, "displayIndex", "expectFunctions");
        List<Receive> airConditionReceives = airConditionReceiverReader.read(sheet);
        generatorAirConditionReceives(airConditionReceives);
        System.out.println("done AirConditionReceiverReader size = " + airConditionReceives.size());

        SenderReader airConditionSenderReader = new SenderReader();
        sheet = readerUtils.getSpecificSheet(workbook, "canCompare", "preConditionFunctions", "stepsFunctions");
        List<Send> airConditionSends = airConditionSenderReader.read(sheet);
        generatorAirConditionSends(airConditionSends);
        System.out.println("done AirConditionSenderReader size = " + airConditionSends.size());

        CanActionsReader canActionsReader = new CanActionsReader();
        sheet = readerUtils.getSpecificSheet(workbook, "dependency");
        List<CanAction> canActions = canActionsReader.read(sheet);
        generatorCanAction(canActions);
        System.out.println("done CanActionsReader size = " + canActions.size());

        ClusterReader clusterReader = new ClusterReader();
        sheet = readerUtils.getSpecificSheet(workbook, "position", "expectFunctions");
        List<Cluster> clusters = clusterReader.read(sheet);
        generatorCluster(clusters);
        System.out.println("done ClusterReader size = " + clusters.size());

        ScreenActionsReader screenActionsReader = new ScreenActionsReader();
        sheet = readerUtils.getSpecificSheet(workbook, "position", "continueTime");
        List<ScreenAction> screenActions = screenActionsReader.read(sheet);
        generatorScreenAction(screenActions);
        System.out.println("done ScreenActionsReader size = " + screenActions.size());


        excelUtils.close(workbook);
        generatorOther();
        generatorScreenshotAndImageCompare();
        generatorPortConfigAndDirector();

    }

    @SneakyThrows
    private static void generatorScreenAction(List<ScreenAction> screenActions) {
        Path screenActionPath = Paths.get(FilesUtils.getCurrentPath() + File.separator + "screen_action.py");
        FilesUtils.deleteFiles(screenActionPath);
        CodeBuilder codeBuilder = new CodeBuilder();
        codeBuilder.build("screenactions", screenActions, TestCaseTypeEnum.COMMON, screenActionPath, null);
    }

    @SneakyThrows
    private static void generatorAirConditionSends(List<Send> airConditionSends, Function function) {
        Path espPath = Paths.get(FilesUtils.getCurrentPath() + File.separator + "esp.py");
        FilesUtils.deleteFiles(espPath);
        CodeBuilder codeBuilder = new CodeBuilder();
        codeBuilder.setTestCaseName("esp");
        codeBuilder.build("testcase", airConditionSends.stream()
                .filter(airConditionReceive -> airConditionReceive.getCategory().equalsIgnoreCase("ADAS"))
                .collect(Collectors.toList()), TestCaseTypeEnum.AIR_CONDITION, espPath, function);
    }

    @SneakyThrows
    private static void generatorAirConditionReceives(List<Receive> airConditionReceives) {
        Path adasPath = Paths.get(FilesUtils.getCurrentPath() + File.separator + "adas.py");
        Path adasComparePath = Paths.get(FilesUtils.getCurrentPath() + File.separator + "adas_compare.py");
        FilesUtils.deleteFiles(adasPath, adasComparePath);
        CodeBuilder codeBuilder = new CodeBuilder();
        codeBuilder.setTestCaseName("adas");
        List<Receive> adass = airConditionReceives.stream()
                .filter(airConditionReceive -> airConditionReceive.getCategory().equalsIgnoreCase("ADAS"))
                .collect(Collectors.toList());
        codeBuilder.build("actiontestcase", adass, TestCaseTypeEnum.AIR_CONDITION, adasPath);
        codeBuilder.build("comparetestcase", adass, TestCaseTypeEnum.AIR_CONDITION, adasComparePath);
    }

    @SneakyThrows
    private static void generatorCluster(List<Cluster> clusters) {
        Path bcmPath = Paths.get(FilesUtils.getCurrentPath() + File.separator + "bcm.py");
        FilesUtils.deleteFiles(bcmPath);
        CodeBuilder codeBuilder = new CodeBuilder();
        codeBuilder.setTestCaseName("bcm");
        codeBuilder.build("testcase", clusters.stream()
                .filter(cluster -> cluster.getCategory().equalsIgnoreCase("BCM"))
                .collect(Collectors.toList()), TestCaseTypeEnum.CLUSTER, bcmPath);
    }

    @SneakyThrows
    public static void generatorPortConfigAndDirector() {
        CodeBuilder codeBuilder = new CodeBuilder();
        Path airConditionDirector = Paths.get(FilesUtils.getCurrentPath() + File.separator + "ac_condition_director.py");
        Path clusterDirector = Paths.get(FilesUtils.getCurrentPath() + File.separator + "cluster_director.py");
        Path portConfig = Paths.get(FilesUtils.getCurrentPath() + File.separator + "port_config.py");
        FilesUtils.deleteFiles(airConditionDirector, clusterDirector, portConfig);
        codeBuilder.build("director", null, TestCaseTypeEnum.AIR_CONDITION, airConditionDirector);
        codeBuilder.build("director", null, TestCaseTypeEnum.CLUSTER, clusterDirector);
        codeBuilder.build("portconfig", null, TestCaseTypeEnum.AIR_CONDITION, portConfig);
    }


    @SneakyThrows
    public static void generatorScreenshotAndImageCompare() {
        CodeBuilder codeBuilder = new CodeBuilder();
        Path airConditionScreenshot = Paths.get(FilesUtils.getCurrentPath() + File.separator + "ac_condition_screen_shot.py");
        Path clusterScreenshot = Paths.get(FilesUtils.getCurrentPath() + File.separator + "cluster_screen_shot.py");
        Path airConditionImageCompare = Paths.get(FilesUtils.getCurrentPath() + File.separator + "ac_condition_image_compare.py");
        Path clusterImageCompare = Paths.get(FilesUtils.getCurrentPath() + File.separator + "cluster_image_compare.py");
        FilesUtils.deleteFiles(airConditionScreenshot, clusterScreenshot, airConditionImageCompare, clusterImageCompare);
        codeBuilder.build("screenshot", null, TestCaseTypeEnum.AIR_CONDITION, airConditionScreenshot);
        codeBuilder.build("imagecompare", null, TestCaseTypeEnum.AIR_CONDITION, airConditionImageCompare);
        codeBuilder.build("screenshot", null, TestCaseTypeEnum.AIR_CONDITION, clusterScreenshot);
        codeBuilder.build("imagecompare", null, TestCaseTypeEnum.AIR_CONDITION, clusterImageCompare);

    }


    public static void generatorCanAction(List<CanAction> canActions) {
        Path actions = Paths.get(FilesUtils.getCurrentPath() + File.separator + "actions.py");
        generator("actions", canActions.stream().filter(canAction -> canAction.getCategory().equalsIgnoreCase(ACTION)).collect(Collectors.toList()), actions);
        Path conditions = Paths.get(FilesUtils.getCurrentPath() + File.separator + "conditions.py");
        generator("conditions", canActions.stream().filter(canAction -> canAction.getCategory().equalsIgnoreCase(CONDITION)).collect(Collectors.toList()), conditions);
    }

    public static void generatorOther() {
        Path imageProperty = Paths.get(FilesUtils.getCurrentPath() + File.separator + "image_property.py");
        generatorCommon("imageproperty", imageProperty);
        Path clusterService = Paths.get(FilesUtils.getCurrentPath() + File.separator + "cluster_service.py");
        generator(clusterService, true);
        Path airConditionService = Paths.get(FilesUtils.getCurrentPath() + File.separator + "air_condition_service.py");
        generator(airConditionService, false);
    }

    @SneakyThrows
    public static void generator(Path path, boolean type) {
        FilesUtils.deleteFiles(path);
        CodeBuilder codeBuilder = new CodeBuilder();
        codeBuilder.setProjectName("faw");
        if (type) {
            codeBuilder.build("service", null, TestCaseTypeEnum.CLUSTER, path);
        } else {
            codeBuilder.build("service", null, TestCaseTypeEnum.AIR_CONDITION, path);
        }

    }


    @SneakyThrows
    public static void generatorCommon(String templateName, Path path) {
        FilesUtils.deleteFiles(path);
        CodeBuilder codeBuilder = new CodeBuilder();
        codeBuilder.build(templateName, null, TestCaseTypeEnum.COMMON, path);
    }

    @SneakyThrows
    public static void generator(String templateName, List<CanAction> canActions, Path path) {
        FilesUtils.deleteFiles(path);
        CodeBuilder codeBuilder = new CodeBuilder();
        codeBuilder.build(templateName, canActions, TestCaseTypeEnum.COMMON, path);
    }


    public static void main(String[] args) {
        test1();
    }*/
}
