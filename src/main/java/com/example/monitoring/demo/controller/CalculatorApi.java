package com.example.monitoring.demo.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.example.monitoring.demo.exception.CalculatorException;
import com.example.monitoring.demo.service.CalculatorService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CalculatorApi {
    private static final Logger LOG = LoggerFactory.getLogger(CalculatorApi.class);

    @Autowired
    CalculatorService calculator;
    
    /**
     * POST cal as JSON
     * http://localhost:8080/api/cal/post
     *  {
     *     "op": "5",
     *     "left": "3",
     *     "right": "4"
     *  }
     * @return
     */
    @PostMapping(value = { "/cal" }, produces = "application/json")
    public ResponseEntity<Map<String, String>> calculation(
            final @RequestBody(required = false) Map<String, String> body) {

        LOG.info("/api/cal Started");

        ResponseEntity<?> entRes = null;
        Map<String, String> response = new HashMap<String, String>();
        response.put("Service", "/api/cal/post");

        String message = "null";

        String operator = null;
        String leftOperand = null;
        String rightOperand = null;

        if (body != null) {
            if (body.containsKey("op")) {
                operator = body.get("op");
                LOG.debug("Operater: [{}]", operator);
            } else {
                throw new CalculatorException("Missing Operator");
            }

            if (body.containsKey("left")) {
                leftOperand = body.get("left");
                LOG.debug("leftOperand: [{}]", leftOperand);
            } else {
                throw new CalculatorException("Missing LEFT");
            }

            if (body.containsKey("right")) {
                rightOperand = body.get("right");
                LOG.debug("rightOperand: [{}]", rightOperand);
            } else {
                throw new CalculatorException("Missing RIGHT");
            }
        } else {
            message = "No POST Body";
            throw new CalculatorException("Missing POST Body");
        }

        if (StringUtils.isNotBlank(operator) && StringUtils.isNotBlank(leftOperand)
                && StringUtils.isNotBlank(rightOperand)) {

            double leftNumber = calculator.getLeftNumber(leftOperand);
            double rightNumber = calculator.getrightNumber(rightOperand);

            calculator.setOperator(operator);
            calculator.setLeftOperand(leftNumber);
            calculator.setRightOperand(rightNumber);

            double result = calculator.calculateResult();

                response.put("Status", "Success");
                response.put("StatusCode", "200");
                response.put("Message", message);
                response.put("Operator", operator);
                response.put("LeftOperand", leftOperand);
                response.put("RightOperand", rightOperand);
                response.put("Result", String.valueOf(result));
        } else {
                LOG.error("Calculation failed");
                response.put("Status", "Failed");
                response.put("StatusCode", "500");
                response.put("Message", "Failed.");
                response.put("Operator", operator);
                response.put("LeftOperand", leftOperand);
                response.put("RightOperand", rightOperand);
        }

        entRes = ResponseEntity.status(HttpStatus.OK).body(Collections.unmodifiableMap(response));
        @SuppressWarnings("unchecked")
        final ResponseEntity<Map<String, String>> resEnt = (ResponseEntity<Map<String, String>>) entRes;

        LOG.info("/api/cal Finished");
        return resEnt;
    }

}
