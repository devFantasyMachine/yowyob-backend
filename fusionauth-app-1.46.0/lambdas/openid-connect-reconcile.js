// This is the default OpenID Connect reconcile, modify this to your liking.
function reconcile(user, registration, jwt, idToken) {
  // When the openid scope was requested, and the IdP returns an id_token, this value will optionally
  // be available to this lambda if signed using the client secret using HS256, HS384, or the HS512 algorithm.

  // Un-comment this line to see the jwt object printed to the event log
  // console.info(JSON.stringify(jwt, null, 2));

  user.firstName = jwt.given_name;
  user.middleName = jwt.middle_name;
  user.lastName = jwt.family_name;
  user.fullName = jwt.name;
  user.imageUrl = jwt.picture;
  user.mobilePhone = jwt.phone_number;

  // https://openid.net/specs/openid-connect-core-1_0.html#StandardClaims
  if (jwt.birthdate && jwt.birthdate !== '0000') {
    if (jwt.birthdate.length === 4) {
      // Only a year was provided, set to January 1.
      user.birthDate = jwt.birthdate + '-01-01';
    } else {
      user.birthDate = jwt.birthdate;
    }
  }

  // https://openid.net/specs/openid-connect-core-1_0.html#StandardClaims
  if (jwt.locale) {
    user.preferredLanguages = user.preferredLanguages || [];
    // Replace the dash with an under_score.
    user.preferredLanguages.push(jwt.locale.replace('-', '_'));
  }

  // Set preferred_username in registration.
  // - This is just for display purposes, this value cannot be used to uniquely identify
  //   the user in FusionAuth.
  registration.username = jwt.preferred_username;
}