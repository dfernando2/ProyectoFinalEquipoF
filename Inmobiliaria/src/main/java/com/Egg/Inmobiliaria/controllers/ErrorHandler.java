package com.Egg.Inmobiliaria.controllers;


import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorHandler implements ErrorController {

    @RequestMapping(value = "error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView paginaError(HttpServletRequest httpRequest){

        ModelAndView error = new ModelAndView("error");

        String msg = "";
        String url = "";

        int codigoHttp = codigoError(httpRequest);

        switch (codigoHttp) {
            case 400:{
                msg = "El recurso solicitado no existe";
                url = "/image/error400.jpg";
                break;
            }

            case 401:{
                msg = "No tiene permisos para acceder a esta página";
                url = "/image/error401.jpg";
                break;
            }

            case 403:{
                msg = "No tiene permisos para acceder al recurso";
                url = "/image/error403.jpg";
                break;
            }

            case 404:{
                msg = "No se ha encontrado la página solicitada";
                url = "/image/error404.jpg";

                break;
            }

            case 500:{
                msg = "Ocurrio un error interno";
                url = "/image/error500.jpg";
                break;
            }
        }
        error.addObject("msg", msg);
        error.addObject("codigoHttp", codigoHttp);
        error.addObject("url", url);

        return error;

    }

    private int codigoError(HttpServletRequest httpRequest){
            return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    public String getErrorPath(){
        return "error";
    }
}
    
