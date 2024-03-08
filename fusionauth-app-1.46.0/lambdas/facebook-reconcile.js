// This is the default Facebook reconcile, modify this to your liking.
function reconcile(user, registration, facebookUser) {

  // Un-comment this line to see the facebookUser object printed to the event log
  // console.info(JSON.stringify(facebookUser, null, 2));

  user.firstName = facebookUser.first_name;
  user.middleName = facebookUser.middle_name;
  user.lastName = facebookUser.last_name;
  user.fullName = facebookUser.name;

  if (facebookUser.picture && !facebookUser.picture.data.is_silhouette) {
    user.imageUrl = facebookUser.picture.data.url;
  }

  if (facebookUser.birthday) {
    // Convert MM/dd/yyyy -> YYYY-MM-DD
    var parts = facebookUser.birthday.split('/');
    user.birthDate = parts[2] + '-' + parts[0] + '-' + parts[1];
  }
}