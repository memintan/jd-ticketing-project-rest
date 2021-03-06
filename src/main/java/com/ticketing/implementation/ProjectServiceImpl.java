package com.ticketing.implementation;

import com.ticketing.dto.ProjectDTO;
import com.ticketing.entitiy.Project;
import com.ticketing.entitiy.User;
import com.ticketing.enums.Status;
import com.ticketing.exception.TicketingProjectException;
import com.ticketing.util.MapperUtil;
import com.ticketing.repository.ProjectRepository;
import com.ticketing.repository.UserRepository;
import com.ticketing.service.ProjectService;
import com.ticketing.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private UserService userService;
    //private TaskService taskService;
    private MapperUtil mapperUtil;

    public ProjectServiceImpl(UserRepository userRepository, ProjectRepository projectRepository, UserService userService,MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.userService = userService;

        this.mapperUtil = mapperUtil;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project = projectRepository.findByProjectCode(code);
        return mapperUtil.convert(project,new ProjectDTO());
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> list = projectRepository.findAll(Sort.by("projectCode"));
        return list.stream().map(obj -> mapperUtil.convert(obj,new ProjectDTO())).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO save(ProjectDTO dto) throws TicketingProjectException {

        Project foundProject = projectRepository.findByProjectCode(dto.getProjectCode());

        if(foundProject != null){
            throw new TicketingProjectException("Project with this code already existing");
        }

        Project obj = mapperUtil.convert(dto,new Project());

        Project createdProject = projectRepository.save(obj);

        return mapperUtil.convert(createdProject,new ProjectDTO());

    }

    @Override
    public ProjectDTO update(ProjectDTO dto) throws TicketingProjectException {

        Project project = projectRepository.findByProjectCode(dto.getProjectCode());

        if(project == null){
            throw new TicketingProjectException("Project does not exist");
        }

        Project convertedProject = mapperUtil.convert(dto,new Project());

        Project updatedProject = projectRepository.save(convertedProject);

        return mapperUtil.convert(updatedProject,new ProjectDTO());

    }

    @Override
    public void delete(String code) throws TicketingProjectException {

        Project project = projectRepository.findByProjectCode(code);

        if(project == null){
            throw new TicketingProjectException("Project does not exist");
        }

        project.setIsDeleted(true);

        project.setProjectCode(project.getProjectCode() +  "-" + project.getId());

        projectRepository.save(project);

        //taskService.deleteByProject(mapperUtil.convert(project,new ProjectDTO()));
    }

    @Override
    public ProjectDTO complete(String projectCode) throws TicketingProjectException {

        Project project = projectRepository.findByProjectCode(projectCode);

        if(project == null){
            throw new TicketingProjectException("Project does not exist");
        }

        project.setProjectStatus(Status.COMPLETE);
        Project completedProject = projectRepository.save(project);

        return mapperUtil.convert(completedProject,new ProjectDTO());
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() throws TicketingProjectException {

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Long currentId = Long.parseLong(id);

        User user = userRepository.findById(currentId).orElseThrow(() -> new TicketingProjectException("This manager does not exists"));

        List<Project> list = projectRepository.findAllByAssignedManager(user);

        if(list.size() == 0 ){
            throw new TicketingProjectException("This manager does not have any project assigned");
        }

        return list.stream().map(project -> {
            ProjectDTO obj = mapperUtil.convert(project,new ProjectDTO());
//            obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTasks(project.getProjectCode()));
//            obj.setCompleteTaskCounts(taskService.totalCompletedTasks(project.getProjectCode()));
            return obj;
        }).collect(Collectors.toList());



    }

    @Override
    public List<ProjectDTO> readAllByAssignedManager(User user) {
        List<Project> list = projectRepository.findAllByAssignedManager(user);
        return list.stream().map(obj ->mapperUtil.convert(obj,new ProjectDTO())).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listAllNonCompletedProjects() {

        return projectRepository.findAllByProjectStatusIsNot(Status.COMPLETE)
                .stream()
                .map(project -> mapperUtil.convert(project,new ProjectDTO()))
                .collect(Collectors.toList());
    }
}