package org.aquam.learnrest.model;

public enum UserRole {
    ROLE_STUDENT,
    ROLE_TEACHER,
    ROLE_ADMIN       // ?
}
// authentication - who you are (username, password...) - verify
// Method: Login form, HTTP auth, Custom auth
// !!! The user logs in the first time and we give her the token

// authorization - which role and according to the role permission they have
// - decide if I have permission to access a resource
// Method: Access Control URLs, Access Control List (ACLs)
// !!! Validate the token, check permissions
