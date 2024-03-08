/*
 * Copyright (c) 2023. Create by Yowyob
 *//*


package cm.yowyob.auth.app.application.filters;


import cm.yowyob.letsgo.auth.service.domain.exceptions.UnrecognizedUserAgentException;
import cm.yowyob.letsgo.auth.service.infrastructure.models.UserAgent;
import cm.yowyob.letsgo.auth.service.utils.HeaderUtils;
import cm.yowyob.letsgo.auth.service.utils.UserAgentUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


// TODO go a head
public class YowyobUserAgentAuthorizationFilter extends OncePerRequestFilter {



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String userAgentString = request.getHeader(HttpHeaders.USER_AGENT);
		final String userAgentSt = request.getHeader(HeaderUtils.X_YOWYOB_AGENT);

		System.out.println(userAgentSt);

		try {

			final UserAgent userAgent = UserAgentUtil.parseUserAgentString(userAgentSt);
			System.out.println(userAgentString);
			System.out.println(userAgent);

		} catch (UnrecognizedUserAgentException ignored) {
			System.out.println("unknow useragent");
		}

		System.out.println("user ip " + extractIp(request));
		chain.doFilter(request, response);
	}


	private String extractIp(HttpServletRequest request) {

		String clientXForwardedForIp = request.getHeader(HeaderUtils.X_FORWARDED_FOR);

		return HeaderUtils.getMostRecentProxy(clientXForwardedForIp)
				.orElse(request.getRemoteAddr());

	}

	private String parseXForwardedHeader(String clientXForwardedForIp) throws UnknownHostException {

		return InetAddress.getByName(clientXForwardedForIp).toString();
	}

*/
/*
	private String getIpLocation(String ip) throws UnknownHostException {

		String location = UNKNOWN;

		InetAddress ipAddress = InetAddress.getByName(ip);

		CityResponse cityResponse = databaseReader
				.city(ipAddress);

		if (Objects.nonNull(cityResponse) &&
				Objects.nonNull(cityResponse.getCity()) &&
				!StringUtils.isNullOrEmpty(cityResponse.getCity().getId())) {
			location = cityResponse.getCity().getId();
		}
		return location;
	}

	private static String resolveIpVersion(String forwardedFor) {

		return HeaderUtils.getMostRecentProxy(forwardedFor)
				.map(ipString -> {
					try {
						//noinspection UnstableApiUsage
						final InetAddress addr = InetAddress.getByAddress(ipString.getBytes(StandardCharsets.UTF_8));
						if (addr instanceof Inet4Address) {
							return "IPv4";
						}
						if (addr instanceof Inet6Address) {
							return "IPv6";
						}
					} catch (IllegalArgumentException | UnknownHostException e) {
						// ignore illegal argument exception
					}
					return null;
				})
				.orElse("unresolved");
	}
*//*


//
//
//	public void verifyDevice(User user, HttpServletRequest request) throws UnknownHostException {
//
//		String ip = extractIp(request);
//		String location = getIpLocation(ip);
//
//		String deviceDetails = getDeviceDetails(request.getHeader(HttpHeaders.USER_AGENT));
//
//		Device existingDevice = findExistingDevice(user.getUserId(), deviceDetails, location);
//
//		if (Objects.isNull(existingDevice)) {
//
//			unknownDeviceNotification(deviceDetails, location,
//					ip, user.getEmail(), request.getLocale());
//
//			Device deviceMetadata = new Device();
//			deviceMetadata.setUserId(user.getUserId());
//			deviceMetadata.setLocation(location);
//			deviceMetadata.setDeviceDetails(deviceDetails);
//			deviceMetadata.setLastLoggedIn(LocalDateTime.now());
//			deviceRepository.save(deviceMetadata);
//
//		} else {
//
//			existingDevice.setLastLoggedIn(LocalDateTime.now());
//			deviceRepository.save(existingDevice);
//		}
//	}
//
//	private Device findExistingDevice(
//			Long userId, String deviceDetails, String location) {
//
//		List<Device> knownDevices = deviceRepository.findByUserId(userId);
//
//		for (Device existingDevice : knownDevices) {
//			if (existingDevice.getDeviceDetails().equals(deviceDetails)
//					&& existingDevice.getLocation().equals(location)) {
//				return existingDevice;
//			}
//		}
//		return null;
//	}
//
//
//	private String getDeviceDetails(String userAgent) {
//		String deviceDetails = UNKNOWN;
//
//		UserAgent userAgent1 = parser.parse(userAgent);
//
//		if (Objects.nonNull(client)) {
//			deviceDetails = client.userAgent.family
//					+ " " + client.userAgent.major + "."
//					+ client.userAgent.minor + " - "
//					+ client.os.family + " " + client.os.major
//					+ "." + client.os.minor;
//		}
//		return deviceDetails;
//	}

}*/
