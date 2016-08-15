package com.yimei.finance.controllers.admin.tpl;

import com.yimei.finance.entity.tpl.TaskMess;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangqi on 16/8/9.
 */
@Controller
@RequestMapping("/tpl")
public class MyController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngine processEngine;


    @RequestMapping("")
    public String index() {
        return "tpl/apply";

    }

    @RequestMapping("/start")
    public String startInterview(@RequestParam("username") String username, Map<String, Object> mode) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("username", username);
        runtimeService.startProcessInstanceByKey("firstprocess", variables);
        return "tpl/index";

    }



    @RequestMapping("/hary")
    public String assign(Model model) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("username", "hary");
        List<Task> tasksAssignee = taskService.createTaskQuery().taskAssignee("hary").list();
        List<TaskMess> mess = new ArrayList();

        for(Task t:tasksAssignee){
            Execution exe = runtimeService.createExecutionQuery().executionId(t.getExecutionId()).singleResult();
            String processId = exe.getProcessInstanceId();

            //String processId = t.getProcessDefinitionId();




            TaskMess temp = new TaskMess();
            temp.setTask(t);
            temp.setUsername((String)runtimeService.getVariable(exe.getId(), "username"));
            temp.setProcessId(processId);
            mess.add(temp);
            System.out.println("hahahahahahhahah"+temp.getUsername());

        }

        for (TaskMess tt : mess) {
            System.out.println(tt.getUsername()+"~~~~~"+tt.getProcessId());
        }

        model.addAttribute("tasks", mess);
        return "tpl/hary";

    }

    @RequestMapping("/haryassign")
    public String haryAssign(String name, String taskId){
        System.out.println(name+"  !!!!!!!!!!!!!!  "+taskId);
        taskService.setVariable(taskId, "todoUser", name);
        taskService.complete(taskId);
        return "redirect:/tpl/process?role=" + name;
    }



    @RequestMapping("/process")
    public String managerInterview(@RequestParam("role") String username,Map<String, Object> mode) {
        List<Task> tasksAssignee = taskService.createTaskQuery().taskAssignee(username).list();
        List<Task> tasksCandidateUser = taskService.createTaskQuery().taskCandidateUser(username).list();
//	   if(tasksAssignee.size()==0){
//		   System.out.println("您没有需要处理的任务");
//		   return "error";
//	   }

        List<TaskMess> mess = new ArrayList();

        for(Task t:tasksAssignee){
            Execution exe = runtimeService.createExecutionQuery().executionId(t.getExecutionId()).singleResult();
            String processId = exe.getProcessInstanceId();

            //String processId = t.getProcessDefinitionId();



            //  System.out.println(username+"通过"+runtimeService.getVariable(exe.getId(), "username"));
            TaskMess temp = new TaskMess();
            temp.setTask(t);
            temp.setUsername((String)runtimeService.getVariable(exe.getId(), "username"));
            temp.setProcessId(processId);
            mess.add(temp);
            //taskService.complete(t.getId());

        }


        List<TaskMess> mess2 = new ArrayList();

        for(Task t:tasksCandidateUser){
            Execution exe = runtimeService.createExecutionQuery().executionId(t.getExecutionId()).singleResult();
            String processId = exe.getProcessInstanceId();
            //  System.out.println(username+"通过"+runtimeService.getVariable(exe.getId(), "username"));
            TaskMess temp = new TaskMess();
            temp.setTask(t);
            temp.setUsername((String)runtimeService.getVariable(exe.getId(), "username"));
            temp.setProcessId(processId);
            mess2.add(temp);
          //  taskService.complete(t.getId());
        }





        mode.put("tasks", mess);
        mode.put("tasks2", mess2);
        return "tpl/success";
    }

    @RequestMapping("/apply/{id}/{exeid}")
    public String apply(@PathVariable("id") String id, @PathVariable("exeid") String exeid, Map<String, Object> mode) {
        //public String apply(@RequestParam("task") Task task,Map<String, Object> mode) {
        Execution exe = runtimeService.createExecutionQuery().executionId(exeid).singleResult();
        System.out.println("通过"+runtimeService.getVariable(exe.getId(), "username"));
        taskService.complete(id);
        return "tpl/ok";
    }

    @RequestMapping("/claim/{id}/{exeid}")
    public String claimApply(@PathVariable("id") String id,@PathVariable("exeid") String exeid,Map<String, Object> mode) {
        //public String apply(@RequestParam("task") Task task,Map<String, Object> mode) {
        Execution exe = runtimeService.createExecutionQuery().executionId(exeid).singleResult();
        System.out.println("claim"+runtimeService.getVariable(exe.getId(), "username"));
        taskService.claim(id, "hr");
        return "tpl/ok";
    }

    @RequestMapping("/image/{processId}")
    public void gene(HttpServletRequest request, HttpServletResponse response, @PathVariable("processId") String processId){
        FileInputStream fis = null;
        response.setContentType("image/gif");
        try {
            OutputStream out = response.getOutputStream();
            //Command cmd=null;
            //这里输入的流程ID，不是KEY
            //cmd=new GetDeploymentProcessDiagramCmd(processId);
            ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processId).singleResult();

            RepositoryServiceImpl rs = (RepositoryServiceImpl)processEngine.getRepositoryService();

            ProcessDefinitionEntity pd = (ProcessDefinitionEntity) rs.getDeployedProcessDefinition(pi.getProcessDefinitionId());
            BpmnModel bpmnModel =
                    repositoryService.getBpmnModel(pi.getProcessDefinitionId());

            //ProcessDiagramGenerator.generateDiagram
            InputStream is = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
                    .generateDiagram(bpmnModel, "png", runtimeService.getActiveActivityIds(pi.getId()));
            //InputStream is = ProcessDiagramGenerator.generateDiagram(pd,"png",
            //	runtimeService.getActiveActivityIds(processId));
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int len = 0;
            while((len=is.read())!=-1){
                bytestream.write(len);
            }
            byte[] b = bytestream.toByteArray();
            bytestream.close();
            out.write(b);

//				while ((len = is.read(b, 0, 1024)) != -1) {
//					System.out.write(b, 0, len);
//					out.write(b);
//					}
            out.flush();




//		        File file = new File("timg.jpg");
//		        fis = new FileInputStream(file);
//		        byte[] b = new byte[fis.available()];
//		        fis.read(b);
//		        out.write(b);
//		        out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

