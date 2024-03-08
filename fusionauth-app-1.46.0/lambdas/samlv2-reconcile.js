// This is the default SAML v2 reconcile, modify this to your liking.
function reconcile(user, registration, samlResponse) {

  // Un-comment this line to see the samlResponse object printed to the event log
  // console.info(JSON.stringify(samlResponse, null, 2));

  var getAttribute = function(samlResponse, attribute) {
    var values = samlResponse.assertion.attributes[attribute];
    if (values && values.length > 0) {
      return values[0];
    }

    return null;
  };

  // Retrieve an attribute from the samlResponse
  // - Arguments [2 .. ] provide a preferred order of attribute names to lookup the value in the response.
  var defaultIfNull = function(samlResponse) {
    for (var i = 1; i < arguments.length; i++) {
      var value = getAttribute(samlResponse, arguments[i]);
      if (value !== null) {
        return value;
      }
    }
  };

  user.birthDate = defaultIfNull(samlResponse, 'http://schemas.xmlsoap.org/ws/2005/05/identity/claims/dateofbirth', 'birthdate', 'date_of_birth');
  user.firstName = defaultIfNull(samlResponse, 'http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname', 'first_name');
  user.lastName = defaultIfNull(samlResponse, 'http://schemas.xmlsoap.org/ws/2005/05/identity/claims/surname', 'last_name');
  user.fullName = defaultIfNull(samlResponse, 'http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name', 'name', 'full_name');
  user.mobilePhone = defaultIfNull(samlResponse, 'http://schemas.xmlsoap.org/ws/2005/05/identity/claims/mobilephone', 'mobile_phone');
}