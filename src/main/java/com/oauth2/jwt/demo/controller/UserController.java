package com.oauth2.jwt.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

  @GetMapping("/lookup")
  public ResponseEntity<Principal> get(final Principal principal) {
    return ResponseEntity.ok(principal);
  }
}
