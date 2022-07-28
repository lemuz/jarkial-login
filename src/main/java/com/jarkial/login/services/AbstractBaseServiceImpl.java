package com.jarkial.login.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonView;
import com.jarkial.login.configuration.utils.MyUtils;
import com.jarkial.login.configuration.utils.MyUtilsConstant;

public abstract class AbstractBaseServiceImpl<C, I> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final int NUMBER_OF_RECORDS_PER_PAGE = 25;

    protected String getPrincipal(){ return SecurityContextHolder.getContext().getAuthentication().getName();}

    protected Pageable constructPageSpecification(int pageIndex){
        Pageable pageSpecification = new PageRequest((pageIndex>0?(pageIndex-1):pageIndex), NUMBER_OF_RECORDS_PER_PAGE);
        return pageSpecification;
    }

    protected Pageable constructPageSpecification(int pageIndex, int recordsPerPage){
        Pageable pageSpecification = new PageRequest((pageIndex>0?(pageIndex-1):pageIndex), recordsPerPage);
        return pageSpecification;
    }

    protected Pageable constructPageSpecification(int pageIndex, List<String> sortProperties, int recordsPerPage){
        Pageable pageSpecification;
        if(sortProperties.size() >0) {
            List<Sort.Order> orders = new ArrayList<>();
            for(int i=0; i < sortProperties.size(); i++){
                Sort.Order tmp = new Sort.Order(sortProperties.get(i));
                orders.add(tmp);
            }
            pageSpecification = new PageRequest((pageIndex>0? (pageIndex -1) : pageIndex), recordsPerPage, sortByList(orders));
        }else{
            pageSpecification = new PageRequest((pageIndex>0? (pageIndex -1) : pageIndex), recordsPerPage);
        }
        return pageSpecification;
    }

    protected Pageable constructPageSpecificationDesc(int pageIndex, String sortProperty, int recordsPerPage){
        Pageable pageSpecification = new PageRequest((pageIndex > 0? (pageIndex -1): pageIndex), NUMBER_OF_RECORDS_PER_PAGE, sortByDesc(sortProperty));
        return pageSpecification;
    }

    protected Pageable constructPageSpecificationAsc(int pageIndex, String sortProperty, int recordsPerPage){
        Pageable pageSpecification = new PageRequest((pageIndex > 0? (pageIndex -1): pageIndex), NUMBER_OF_RECORDS_PER_PAGE, sortByAsc(sortProperty));
        return pageSpecification;
    }

    protected Sort sortByDesc(String sortProperty){ return new Sort(Sort.Direction.DESC, sortProperty);}

    protected Sort sortByAsc(String sortProperty){ return new Sort(Sort.Direction.ASC, sortProperty);}

    protected Sort sortByList(List<Sort.Order> orders){ return new Sort(orders);}

    protected List<String> getNameAnnotatedFields(Object entity, Class jsonViewClass){
        List<String> camposAnotados = new ArrayList<>();
        Field[] campos = entity.getClass().getDeclaredFields();
        for(Field campo: campos){
            if(!campo.isAnnotationPresent(JsonView.class)){
                continue;
            }
            JsonView anotacion = campo.getAnnotation(JsonView.class);
            if(Arrays.asList(anotacion.value()).contains(jsonViewClass)){
                camposAnotados.add(campo.getName());
            }
        }
        return camposAnotados;
    }

    protected void log(String accion, String ip){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info(MyUtils.cleanForLog(getFecha() + "El usuario = {}" + auth.getName() + " realizo un " + accion + " desde la ip: " + ip));
    }

    protected String getFecha(){
        String timeStamp = MyUtilsConstant.dateFormatAsYYYYMMDDHHMMSS.format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    public abstract List<C> findAll() throws Exception;

    public abstract C findById(I id) throws Exception;

    public abstract C update(C entity) throws Exception;

    public abstract boolean deleteById(I id) throws Exception;
}