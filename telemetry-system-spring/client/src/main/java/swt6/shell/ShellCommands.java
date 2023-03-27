package swt6.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.domain.*;
import swt6.interfaces.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.lang.Double.NaN;

@ShellComponent
public class ShellCommands {

  @Autowired
  private ApplicationService applicationService;
  @Autowired
  private ApplicationInstanceService instanceService;
  @Autowired
  private DetectorService detectorService;
  @Autowired
  private MetricService metricService;
  @Autowired
  private LogMessageService logMessageService;
  @Autowired
  private IncidentService incidentService;
  @Autowired
  private DataStatisticService dataStatisticService;
  @Autowired
  private IncidentStatisticService incidentStatisticService;

  @ShellMethod(key="commands", value="List All Command categories")
  public void listCommands(){
    System.out.println("command categories:");
    System.out.println();
    System.out.println("app                   " + "   -- List all app commands --");
    System.out.println("instance              " + "   -- List all instance commands --");
    System.out.println("detector              " + "   -- List all detector commands --");
    System.out.println("telemetryData         " + "   -- List all telemetryData commands --");
    System.out.println("metric                " + "   -- List all metric commands --");
    System.out.println("incident              " + "   -- List all incident commands --");
    System.out.println("dataStatistic         " + "   -- List all statistic commands --");
    System.out.println("incidentStatistic     " + "   -- List all statistic commands --");
  }

  //Application
  @ShellMethod(key = "app", value = "List all app commands")
  public void appCommands(){
    System.out.println("app commands:");
    System.out.println();
    System.out.println("list-apps                 " + "   -- List all applications --");
    System.out.println("list-instances-appid --id " + "   -- List all instances by application id --");
  }

  @ShellMethod(key="list-apps", value="List all applications")
  public void listApplications() {
    for(var a : applicationService.getAllApplications()){
      System.out.println("ID: " + a.getId());
      System.out.println("Platform: " + a.getPlatform());
      System.out.println("Supported OS: ");
      for(var os : a.getSupportedOS()){
        System.out.println("-- " + os);
      }
      System.out.println();
    }
  }

  @ShellMethod(key="list-instances-appid", value="List all instances by application id")
  public void listInstancesByAppId(@ShellOption Long id) {
    Optional<Application> app = applicationService.getApplicationById(id);
    if(app.isEmpty()) {
      System.out.println("No app for id " + id);
      return;
    }

    if(app.get().getInstances().isEmpty()){
      System.out.println("No instances for app id " + id);
      return;
    }

    System.out.println("This app has " + app.get().getInstances().size() + " instances:");
    for(var ai : app.get().getInstances()){
      System.out.println(ai);
    }
  }


  //Application Instance
  @ShellMethod(key = "instance", value = "List all instance commands")
  public void instanceCommands(){
    System.out.println("instance commands:");
    System.out.println();
    System.out.println("list-instances" + "   -- List all instances --");
  }

  @ShellMethod(key="list-instances", value="List all instances")
  public void listInstances() {
    for(var i : instanceService.getAllInstances()){
      System.out.println(i);
    }
    System.out.println();
  }


  //Detector
  @ShellMethod(key = "detector", value = "List all detector commands")
  public void detectorCommands(){
    System.out.println("detector commands:");
    System.out.println();
    System.out.println("list-detectors                                                  " + "   -- List all detectors --");
    System.out.println("list-detectors-appid --id                                       " + "   -- List all detectors by application id --");
    System.out.println("list-detectors-instanceid --id                                  " + "   -- List all detectors by instance id --");
    System.out.println("list-detectors-metricid --id                                    " + "   -- List all detectors by instance id --");
    System.out.println("create-detector --metricid --name --min --max --interval-in-sec " + "   -- List all detectors by instance id --");
  }

  @ShellMethod(key="list-detectors", value="List all detectors")
  public void listDetectors() {
    List<Detector> detectors = detectorService.getAllDetectors();
    if(detectors.isEmpty()){
      System.out.println("There are no detectors in this system");
      return;
    }

    for(var d : detectors){
      System.out.println("Id:       " + d.getId());
      System.out.println("Name:     " + d.getName());
      System.out.println("Min:      " + d.getMin());
      System.out.println("Max:      " + d.getMax());
      System.out.println("Interval: " + d.getInterval());
      System.out.println();
    }
  }

  @ShellMethod(key="list-detectors-appid", value="List all detectors for application id")
  public void listDetectorsByAppId(@ShellOption Long id) {
    List<Detector> detectors = detectorService.getAllDetectorsByApplicationId(id);
    if(detectors.isEmpty()){
      System.out.println("There are no detectors for app id: " + id);
      return;
    }

    System.out.println("Detectors for app id " + id + ":");
    for(var d : detectors){
      System.out.println("Id:       " + d.getId());
      System.out.println("Name:     " + d.getName());
      System.out.println("Min:      " + d.getMin());
      System.out.println("Max:      " + d.getMax());
      System.out.println("Interval: " + d.getInterval());
    }
  }

  @ShellMethod(key="list-detectors-instanceid", value="List all detectors for application instance id")
  public void listDetectorsByInstanceId(@ShellOption Long id) {
    List<Detector> detectors = detectorService.getAllDetectorsByApplicationInstanceId(id);
    if(detectors.isEmpty()){
      System.out.println("There are no detectors for instance id: " + id);
      return;
    }

    System.out.println("Detectors for instance id " + id + ":");
    for(var d : detectors){
      System.out.println("Id:       " + d.getId());
      System.out.println("Name:     " + d.getName());
      System.out.println("Min:      " + d.getMin());
      System.out.println("Max:      " + d.getMax());
      System.out.println("Interval: " + d.getInterval());
    }
  }

  @ShellMethod(key="list-detectors-metricid", value="List all detectors for metric id")
  public void listDetectorsByMetricId(@ShellOption Long id) {
    List<Detector> detectors = detectorService.getAllDetectorsByMetricId(id);
    if(detectors.isEmpty()){
      System.out.println("There are no detectors for metric id: " + id);
      return;
    }

    System.out.println("Detectors for metric id " + id + ":");
    for(var d : detectors){
      System.out.println("Id:       " + d.getId());
      System.out.println("Name:     " + d.getName());
      System.out.println("Min:      " + d.getMin());
      System.out.println("Max:      " + d.getMax());
      System.out.println("Interval: " + d.getInterval());
    }
  }

  @ShellMethod(key = "create-detector", value = "Create Detector for a Metric [metricID] [name] [min] [max] [interval]")
  public void createDetector(@ShellOption Long id, @ShellOption String name, @ShellOption Double min,
                             @ShellOption Double max, @ShellOption Double interval){
    detectorService.scheduleDetector(id, name, min, max, interval);
    System.out.println();
  }


  //Telemetry Data
  @ShellMethod(key = "telemetryData", value = "List all telemetryData commands")
  public void telemetryDataCommands(){
    System.out.println("telemetryData commands:");
    System.out.println();
    System.out.println("list-td                                " + "         -- list all telemetry data --");
    System.out.println("list-td-appid --id                     " + "         -- list all telemetry data by application id --");
    System.out.println("list-td-instanceid --id                " + "         -- list all telemetry data by instance id --");
    System.out.println("delete-td-appid --id                   " + "         -- delete telemetry data by application id --");
    System.out.println("delete-td-instanceid --id              " + "         -- delete telemetry data by instance id --");
    System.out.println("delete-td-ts-before --LocalDateTime    " + "         -- delete telemetry data by timestamp before --");
    System.out.println("delete-td-ts-after --LocalDateTime     " + "         -- delete telemetry data by timestamp after --");
  }

  @ShellMethod(key="list-td", value="List all telemetry data")
  public void listTelemetryData() {
    List<Metric> metrics = metricService.getAllMetrics();
    List<LogMessage> logMessages = logMessageService.getAllLogMessages();

    if(metrics.size() + logMessages.size() == 0){
      System.out.println("There is no data in this system");
      return;
    }

    System.out.println("======== Metrics ========");
    for(var m : metrics){
      System.out.println("ID:         " + m.getId());
      System.out.println("SenderID:   " + m.getSenderID());
      System.out.println("Name:       " + m.getName());
      System.out.println("CommonName: " + m.getCommonName());
      System.out.println("Timestamp:  " +m .getTimestamp());
      System.out.println();
    }
    System.out.println();

    System.out.println("======== LogMessages ========");
    for(var l : logMessages){
      System.out.println("ID:         " + l.getId());
      System.out.println("SenderID:   " + l.getSenderID());
      System.out.println("Name:       " + l.getName());
      System.out.println("CommonName: " + l.getCommonName());
      System.out.println("Timestamp:  " + l.getTimestamp());
      System.out.println("Message:    " + l.getMessage());
      System.out.println("LogLevel:   " + l.getLogLevel());
      System.out.println();
    }
    System.out.println();
  }

  @ShellMethod(key="list-td-appid", value="List all telemetry data by application id")
  public void listTelemetryDataByAppId(Long id) {
    List<Metric> metrics = metricService.getMetricsByApplicationId(id);
    List<LogMessage> logMessages = logMessageService.getAllByApplicationId(id);

    if(metrics.size() + logMessages.size() == 0){
      System.out.println("There is no data in this system");
      return;
    }

    System.out.println("======== Metrics ========");
    for(var m : metrics){
      System.out.println("ID:         " + m.getId());
      System.out.println("SenderID:   " + m.getSenderID());
      System.out.println("Name:       " + m.getName());
      System.out.println("CommonName: " + m.getCommonName());
      System.out.println("Timestamp:  " +m .getTimestamp());
      System.out.println();
    }
    System.out.println();

    System.out.println("======== LogMessages ========");
    for(var l : logMessages){
      System.out.println("ID:         " + l.getId());
      System.out.println("SenderID:   " + l.getSenderID());
      System.out.println("Name:       " + l.getName());
      System.out.println("CommonName: " + l.getCommonName());
      System.out.println("Timestamp:  " + l.getTimestamp());
      System.out.println("Message:    " + l.getMessage());
      System.out.println("LogLevel:   " + l.getLogLevel());
      System.out.println();
    }
    System.out.println();
  }

  @ShellMethod(key="list-td-instanceid", value="List all telemetry data by application instance id")
  public void listTelemetryDataByInstanceId(Long id) {
    List<Metric> metrics = metricService.getMetricsByApplicationInstanceId(id);
    List<LogMessage> logMessages = logMessageService.getAllByApplicationInstanceId(id);

    if(metrics.size() + logMessages.size() == 0){
      System.out.println("There is no data in this system");
      return;
    }

    System.out.println("======== Metrics ========");
    for(var m : metrics){
      System.out.println("ID:         " + m.getId());
      System.out.println("SenderID:   " + m.getSenderID());
      System.out.println("Name:       " + m.getName());
      System.out.println("CommonName: " + m.getCommonName());
      System.out.println("Timestamp:  " +m .getTimestamp());
      System.out.println();
    }
    System.out.println();

    System.out.println("======== LogMessages ========");
    for(var l : logMessages){
      System.out.println("ID:         " + l.getId());
      System.out.println("SenderID:   " + l.getSenderID());
      System.out.println("Name:       " + l.getName());
      System.out.println("CommonName: " + l.getCommonName());
      System.out.println("Timestamp:  " + l.getTimestamp());
      System.out.println("Message:    " + l.getMessage());
      System.out.println("LogLevel:   " + l.getLogLevel());
      System.out.println();
    }
    System.out.println();
  }

  @ShellMethod(key = "delete-td-appid", value = "Delete telemetry data by application id")
  public void deleteTelemetryDataByAppId(@ShellOption Long id){
    metricService.deleteByApplicationId(id);
    logMessageService.deleteByApplicationId(id);
  }

  @ShellMethod(key = "delete-td-instanceid", value = "Delete telemetry data by application instance id")
  public void deleteTelemetryDataByInstanceId(@ShellOption Long id){
    metricService.deleteByApplicationInstanceId(id);
    logMessageService.deleteByApplicationInstanceId(id);
  }

  @ShellMethod(key = "delete-td-ts-before", value = "Delete telemetry data by timestamp before")
  public void deleteTelemetryDataByTimestampBefore(@ShellOption String timestamp){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime datetime = LocalDateTime.parse(timestamp, formatter);
    metricService.deleteByTimestampBefore(datetime);
    logMessageService.deleteByTimestampBefore(datetime);
  }

  @ShellMethod(key = "delete-td-ts-after", value = "Delete telemetry data by timestamp after")
  public void deleteTelemetryDataByTimestampAfter(@ShellOption String timestamp){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime datetime = LocalDateTime.parse(timestamp, formatter);
    metricService.deleteByTimestampAfter(datetime);
    logMessageService.deleteByTimestampAfter(datetime);
  }


  //Incident
  @ShellMethod(key = "incident", value = "List all incident commands")
  public void incidentCommands(){
    System.out.println("Incident commands:");
    System.out.println();
    System.out.println("list-incidents                                       " + " -- List all incidents --");
    System.out.println("list-incidents-appid --id                            " + " -- List all incidents by application id --");
    System.out.println("list-incidents-instid --id                           " + " -- List all incidents by application instance id --");
    System.out.println("delete-inc-appid --id                                " + " -- Delete all incidents by application id --");
    System.out.println("delete-inc-appid-ts-before --id --LocalDateTime      " + " -- Delete all incidents by application id and timestamp before--");
    System.out.println("delete-inc-appid-ts-after --id --LocalDateTime       " + " -- List all incident by application id and timestamp after --");
    System.out.println("delete-inc-instid --id                               " + " -- Delete all incidents by application instance id --");
    System.out.println("delete-inc-instid-ts-before --id --LocalDateTime     " + " -- Delete all incidents by application instance id and timestamp before--");
    System.out.println("delete-inc-instid-ts-after --id --LocalDateTime      " + " -- List all incident by application instance id and timestamp after --");
  }

  @ShellMethod(key = "list-incidents", value = "List all incidents")
  public void listAllIncidents(){
    for(var i : incidentService.getAllIncidents()){
      System.out.println(i.getId() + ": " + i.getNode().getValue() + " " + i.getNode().getTimeStamp());
    }
    System.out.println();
  }

  @ShellMethod(key = "list-incidents-app-id", value = "List all incidents by Application Id")
  public void listAllIncidentsByAppId(@ShellOption Long id){
    List<Incident> incidents = incidentService.getAllIncidentsByApplicationId(id);
    if(incidents == null)
      System.out.println("There are no incidents for app id: " + id);
    else {
      System.out.println("Incidents for app id :" + id);
      for (var i : incidents) {
        System.out.println(i.getId() + ": " + i.getNode().getValue() + " " + i.getNode().getTimeStamp());
      }
    }
    System.out.println();
  }

  @ShellMethod(key = "list-incidents-inst-id", value = "List all incidents by application instance id")
  public void listAllIncidentsByInstanceId(@ShellOption Long id){
    List<Incident> incidents = incidentService.getAllIncidentsByApplicationInstanceId(id);
    if(incidents == null)
      System.out.println("There are no incidents for instance id: " + id);
    else{
      System.out.println("Incidents for application instance id :" + id);
      for(var i : incidents){
        System.out.println(i.getId() + ": " + i.getNode().getValue() + " " + i.getNode().getTimeStamp());
      }
    }
  }

  @ShellMethod(key = "delete-incidents-appid", value = "Delete all incidents by application id")
  public void deleteAllIncidentsByAppId(@ShellOption Long id){
    incidentService.deleteAllByApplicationId(id);
  }

  @ShellMethod(key = "delete-inc-appid-ts-before", value = "Delete all incidents by application id and timestamp before")
  public void deleteAllIncidentsByAppIdAndTimestampBefore(@ShellOption Long id, @ShellOption String timestamp){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime datetime = LocalDateTime.parse(timestamp, formatter);

    incidentService.deleteAllByApplicationIdAndTimestampBefore(id, datetime);
  }

  @ShellMethod(key = "delete-inc-appid-ts-after", value = "Delete all incidents by application id and timestamp after")
  public void deleteAllIncidentsByAppIdAndTimestampAfter(@ShellOption Long id, @ShellOption String timestamp){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime datetime = LocalDateTime.parse(timestamp, formatter);

    incidentService.deleteAllByApplicationIdAndTimestampAfter(id, datetime);
  }

  @ShellMethod(key = "delete-incidents-appid", value = "Delete all incidents by application incidents id")
  public void deleteAllIncidentsByInstanceId(@ShellOption Long id){
    incidentService.deleteAllByApplicationInstanceId(id);
  }

  @ShellMethod(key = "delete-inc-appid-ts-before", value = "Delete all incidents by application instance id and timestamp before")
  public void deleteAllIncidentsByInstanceIdAndTimestampBefore(@ShellOption Long id, @ShellOption String timestamp){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime datetime = LocalDateTime.parse(timestamp, formatter);

    incidentService.deleteAllByApplicationInstanceIdAndTimestampBefore(id, datetime);
  }

  @ShellMethod(key = "delete-inc-appid-ts-after", value = "Delete all incidents by application instance id and timestamp after")
  public void deleteAllIncidentsByInstanceIdAndTimestampAfter(@ShellOption Long id, @ShellOption String timestamp){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime datetime = LocalDateTime.parse(timestamp, formatter);

    incidentService.deleteAllByApplicationInstanceIdAndTimestampAfter(id, datetime);
  }


  // Data Statistics
  @ShellMethod(key = "dataStatistic", value = "List all data statistic commands")
  public void dataStatisticCommands(){
    System.out.println("dataStatistic commands:");
    System.out.println();
    System.out.println("get-avg-metric-appid --id                                               " + " -- Get average value from metric by application id --");
    System.out.println("get-avg-appid-interval --id  --LocalDateTime from --LocalDateTime to    " + " -- Get average value from metric by application id and interval --");
    System.out.println("get-std-dev-metric-appid --id                                           " + " -- Get standard deviation from metric by instance id --");

    System.out.println("get-avg-metric-instid --id                                              " + " -- Get average value from metric by application id --");
    System.out.println("get-avg-instid-interval --id  --LocalDateTime from --LocalDateTime to   " + " -- Get average value from metric by instance id and interval --");
    System.out.println("get-std-dev-metric-instid --id                                          " + " -- Get standard deviation from metric by instance id --");

    System.out.println("get-avg-metric-metid --id                                               " + " -- Get average value from metric by metric id --");
    System.out.println("get-avg-metid-interval --id  --LocalDateTime from --LocalDateTime to    " + " -- Get average value from metric by metric id and interval --");
    System.out.println("get-std-dev-metric-metid --id                                           " + " -- Get standard deviation from metric by metric id --");
  }

  @ShellMethod(key = "get-avg-metric-appid", value = "Get average value from metric by application id")
  public void getAvgValueForMetricByAppId(@ShellOption Long id){
    double res = dataStatisticService.getAvgValueByApplicationId(id);
    if(res == -1 || Double.isNaN(res)){
      System.out.println("There are no metrics for app id: " + id);
      return;
    }
    DecimalFormat df = new DecimalFormat("0.00");

    System.out.println("Average Value for all metrics in application id: " + id);
    System.out.println("AVG: " + df.format(res));
  }

  @ShellMethod(key = "get-avg-appid-interval", value = "Get average value from metric by application id and interval")
  public void getAvgValueForMetricByAppIdAndInterval(@ShellOption Long id, @ShellOption String from,  @ShellOption String to){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime newFrom = LocalDateTime.parse(from, formatter);
    LocalDateTime newTo = LocalDateTime.parse(to, formatter);
    double res = dataStatisticService.getAvgValueByApplicationIdAndInterval(id, newFrom, newTo);

    if(res == -1 || Double.isNaN(res)){
      System.out.println("There are no metrics for app id: " + id);
      return;
    }
    DecimalFormat df = new DecimalFormat("0.00");

    System.out.println("Average Value for all metrics in interval from " + from + " to " + to);
    System.out.println("in app id: " + id);
    System.out.println("AVG: " + df.format(res));
  }

  @ShellMethod(key = "get-std-dev-metric-appid", value = "Get standard deviation from metric by application id")
  public void getAvgStdDevByAppId(@ShellOption Long id){
    double res = dataStatisticService.getStdDeviationByApplicationId(id);
    if(res == -1 || Double.isNaN(res)){
      System.out.println("There are no metrics for app id: " + id);
      return;
    }
    DecimalFormat df = new DecimalFormat("0.00");

    System.out.println("Standard deviation for all metrics in application id: " + id);
    System.out.println("Std dev: " + df.format(res));
  }

  @ShellMethod(key = "get-avg-metric-instid", value = "Get average value from metric by instance id")
  public void getAvgValueForMetricByInstanceId(@ShellOption Long id){
    double res = dataStatisticService.getAvgValueByApplicationInstanceId(id);
    if(res == -1 || Double.isNaN(res)){
      System.out.println("There are no metrics for instance id: " + id);
      return;
    }
    DecimalFormat df = new DecimalFormat("0.00");

    System.out.println("Average Value for all metrics in instance id: " + id);
    System.out.println("AVG: " + df.format(res));
  }

  @ShellMethod(key = "get-avg-instid-interval", value = "Get average value from metric by instance id and interval")
  public void getAvgValueForMetricByInstanceIdAndInterval(@ShellOption Long id, @ShellOption String from,  @ShellOption String to){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime newFrom = LocalDateTime.parse(from, formatter);
    LocalDateTime newTo = LocalDateTime.parse(to, formatter);
    double res = dataStatisticService.getAvgValueByApplicationInstanceIdAndInterval(id, newFrom, newTo);

    if(res == -1 || Double.isNaN(res)){
      System.out.println("There are no metrics for instance id: " + id);
      return;
    }
    DecimalFormat df = new DecimalFormat("0.00");

    System.out.println("Average Value for all metrics in interval from " + from + " to " + to);
    System.out.println("in instance id: " + id);
    System.out.println("AVG: " + df.format(res));
  }

  @ShellMethod(key = "get-std-dev-metric-instid", value = "Get standard deviation from metric by instance id")
  public void getAvgStdDevByInstanceId(@ShellOption Long id){
    double res = dataStatisticService.getStdDeviationByApplicationInstanceId(id);
    if(res == -1 || Double.isNaN(res)){
      System.out.println("There are no metrics for instance id: " + id);
      return;
    }
    DecimalFormat df = new DecimalFormat("0.00");

    System.out.println("Standard deviation for all metrics in instance id: " + id);
    System.out.println("Std dev: " + df.format(res));
  }

  @ShellMethod(key = "get-avg-metric-metid", value = "Get average value from metric by metric id")
  public void getAvgValueForMetricByMetricId(@ShellOption Long id){
    double res = dataStatisticService.getAvgValueByMetricId(id);
    if(res == -1 || Double.isNaN(res)){
      System.out.println("There are no values for metric id: " + id);
      return;
    }
    DecimalFormat df = new DecimalFormat("0.00");

    System.out.println("Average Value for metric id: " + id);
    System.out.println("AVG: " + df.format(res));
  }

  @ShellMethod(key = "get-avg-metid-interval", value = "Get average value from metric id and interval")
  public void getAvgValueForMetricByMetricIdAndInterval(@ShellOption Long id, @ShellOption String from,  @ShellOption String to){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime newFrom = LocalDateTime.parse(from, formatter);
    LocalDateTime newTo = LocalDateTime.parse(to, formatter);
    double res = dataStatisticService.getAvgValueByMetricIdAndInterval(id, newFrom, newTo);

    if(res == -1 || Double.isNaN(res)){
      System.out.println("There are no values for metric id: " + id);
      return;
    }
    DecimalFormat df = new DecimalFormat("0.00");

    System.out.println("Average Value for all values in interval from " + from + " to " + to);
    System.out.println("in metric id: " + id);
    System.out.println("AVG: " + df.format(res));
  }

  @ShellMethod(key = "get-std-dev-metric-metid", value = "Get standard deviation from values by metric id")
  public void getAvgStdDevByMetricId(@ShellOption Long id){
    double res = dataStatisticService.getStdDeviationByApplicationInstanceId(id);
    if(res == -1){
      System.out.println("There are no values for metric id: " + id);
      return;
    }
    DecimalFormat df = new DecimalFormat("0.00");

    System.out.println("Standard deviation for all values in metric id: " + id);
    System.out.println("Std dev: " + df.format(res));
  }

  // Incident Statistics
  @ShellMethod(key = "incidentStatistic", value = "List all incident statistic commands")
  public void incidentStatisticCommands(){
    System.out.println("incident commands:");
    System.out.println();
    System.out.println("get-most-incs-app                                                      " + " -- Get application with most incidents --");
    System.out.println("get-most-incs-app-interval --LocalDateTime from --LocalDateTime to     " + " -- Get application with most incidents in interval --");
    System.out.println("get-most-incs-inst                                                     " + " -- Get instance with most incidents --");
    System.out.println("get-most-incs-inst-interval --LocalDateTime from --LocalDateTime to    " + " -- Get instance with most incidents in interval --");
  }

  @ShellMethod(key = "get-most-incs-app", value = "Get application with most incidents")
  public void getMostIncsForApp(){
    var res = incidentStatisticService.getApplicationWithMostIncidents();
    if(res == null){
      System.out.println("There are no apps with incidents");
      return;
    }
    List<Incident> incidents = incidentService.getAllIncidentsByApplicationId(res.getId());
    int count = incidents.size();

    System.out.println("Application with ID " + res.getId() + "has " + count + " incidents");
  }

  @ShellMethod(key = "get-most-incs-app-interval", value = "Get application with most incidents in interval")
  public void getMostIncsByAppAndInterval(@ShellOption String from, @ShellOption String to){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime newFrom = LocalDateTime.parse(from, formatter);
    LocalDateTime newTo = LocalDateTime.parse(to, formatter);

    var res = incidentStatisticService.getApplicationWithMostIncidentsInInterval(newFrom, newTo);
    if(res == null){
      System.out.println("There are no apps with incidents");
      return;
    }
    List<Incident> incidents = incidentService.getAllIncidentsByApplicationId(res.getId());
    int count = incidents.size();

    System.out.println("Application with ID " + res.getId() + "has " + count + " incidents");
    System.out.println("in interval from " + newFrom + " to " + newTo);
  }

  @ShellMethod(key = "get-most-incs-inst", value = "Get instance with most incidents")
  public void getMostIncsForInstance(){
    var res = incidentStatisticService.getApplicationInstanceWithMostIncidents();
    if(res == null){
      System.out.println("There are no instances with incidents");
      return;
    }
    List<Incident> incidents = incidentService.getAllIncidentsByApplicationInstanceId(res.getId());
    int count = incidents.size();

    System.out.println("Application instance with ID " + res.getId() + "has " + count + " incidents");
  }

  @ShellMethod(key = "get-most-incs-inst-interval", value = "Get instance with most incidents in interval")
  public void getMostIncsByInstanceAndInterval(@ShellOption String from, @ShellOption String to){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime newFrom = LocalDateTime.parse(from, formatter);
    LocalDateTime newTo = LocalDateTime.parse(to, formatter);

    var res = incidentStatisticService.getApplicationInstanceWithMostIncidentsInInterval(newFrom, newTo);
    if(res == null){
      System.out.println("There are no instances with incidents in this interval");
      return;
    }
    List<Incident> incidents = incidentService.getAllIncidentsByApplicationId(res.getId());
    int count = incidents.size();

    System.out.println("Application instance with ID " + res.getId() + "has " + count + " incidents");
    System.out.println("in interval from " + newFrom + " to " + newTo);
  }
















}
