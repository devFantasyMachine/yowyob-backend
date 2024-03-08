// This is the default LinkedIn reconcile, modify this to your liking.
function reconcile(user, registration, linkedInUser) {

  // Un-comment this line to see the linkedInUser object printed to the event log
  // console.info(JSON.stringify(linkedInUser, null, ' '));

  user.firstName = linkedInUser.localizedFirstName || user.firstName;
  user.lastName = linkedInUser.localizedLastName || user.lastName;

  // LinkedIn returns several images sizes.
  // See https://docs.microsoft.com/en-us/linkedin/shared/references/v2/profile/profile-picture
  var images = linkedInUser.profilePicture['displayImage~'].elements || [];
  var image100 = images.length >= 1 ? images[0].identifiers[0].identifier : null;
  var image200 = images.length >= 2 ? images[1].identifiers[0].identifier : null;
  var image400 = images.length >= 3 ? images[2].identifiers[0].identifier : null;
  var image800 = images.length >= 4 ? images[3].identifiers[0].identifier : null;

  // Use the largest image.
  user.imageUrl = image800;

  // Record the LinkedIn Id
  registration.data.linkedIn = registration.data.linkedIn || {};
  registration.data.linkedIn.id = linkedInUser.id;
}