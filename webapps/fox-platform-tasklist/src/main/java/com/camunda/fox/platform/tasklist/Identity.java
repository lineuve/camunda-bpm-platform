package com.camunda.fox.platform.tasklist;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.camunda.fox.platform.tasklist.event.SignInEvent;
import com.camunda.fox.platform.tasklist.event.SignOutEvent;
import com.camunda.fox.platform.tasklist.identity.FoxIdentityService;

@SessionScoped
@Named
public class Identity implements Serializable {

  private static final Logger log = Logger.getLogger(Identity.class.getCanonicalName());
  
  private static final long serialVersionUID = 1L;
  private User currentUser = new User();

  @Inject
  private Event<SignInEvent> signInEvent;
  
  @Inject
  private Event<SignOutEvent> signOutEvent;
  
  @Inject
  private FoxIdentityService foxIdentityService;

  @PostConstruct
  protected void init() {
    log.finest("initializing " + this.getClass().getSimpleName() + " (" + this + ")");
  }
  
  public User getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }

  public void signIn() {
    foxIdentityService.authenticateUser(currentUser.getUsername(), currentUser.getPassword());

    signInEvent.fire(new SignInEvent());
  }

  public String signOut() {
    signOutEvent.fire(new SignOutEvent());
    this.currentUser = new User();
    return "../signin.jsf";
  }

  public boolean isSignedIn() {
    return currentUser.getUsername() != null && currentUser.getUsername().length() > 0 && currentUser.getPassword() != null
            && currentUser.getPassword().length() > 0;
  }
}
