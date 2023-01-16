//package com.sample.library.util;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class SecurityUtil {
//
//    public static String getUserIdentifier() throws SecurityException {
//        Authentication authentication = getAuthentication();
//        if (authentication == null)
//            return null;
//
//        Object obj = authentication.getPrincipal();
//        if (obj instanceof UserDetails) {
//            return ((UserDetails) obj).getUsername();
//        }
//
//        if (authentication instanceof org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//                && obj instanceof String) {
//            return obj.toString();
//        }
//
////        if (authentication instanceof MiscAuthentication
////                && obj instanceof String) {
////            return obj.toString();
////        }
//
//        throw new SecurityException("Can not found identifier in security context");
//    }
//
////    public static boolean hasRole(String roleCode) throws SecurityException {
////        Authentication authentication = getAuthentication();
////        Object object = authentication.getPrincipal();
////        if (object instanceof UserDetails) {
////            for (GrantedAuthority grantedAuthority : ((UserDetails) object).getAuthorities()) {
////                if (grantedAuthority.getAuthority().equals(roleCode))
////                    return true;
////            }
////        } else if (authentication instanceof MiscAuthentication) {
////            return checkAuthorityInMiscAuthentication(Collections.singletonList(roleCode), (MiscAuthentication) authentication);
////        }
////        return false;
////    }
//
////    public static boolean hasRole(List<String> roleCodes) throws SecurityException {
////        Authentication authentication = getAuthentication();
////        Object object = authentication.getPrincipal();
////        if (object instanceof UserDetails) {
////            for (GrantedAuthority grantedAuthority : ((UserDetails) object).getAuthorities()) {
////                if (roleCodes.contains(grantedAuthority.getAuthority()))
////                    return true;
////            }
////        } else if (authentication instanceof MiscAuthentication) {
////            return checkAuthorityInMiscAuthentication(roleCodes, (MiscAuthentication) authentication);
////        }
////        return false;
////    }
//
////    public static boolean hasAnyRole(String... roleCodes) throws SecurityException {
////        List<String> roleCodeList = new ArrayList<>(Arrays.asList(roleCodes));
////        Authentication authentication = getAuthentication();
////        Object object = authentication.getPrincipal();
////        if (object instanceof UserDetails) {
////            for (GrantedAuthority grantedAuthority : ((UserDetails) object).getAuthorities()) {
////                if (roleCodeList.contains(grantedAuthority.getAuthority()))
////                    return true;
////            }
////        } else if (authentication instanceof MiscAuthentication) {
////            return checkAuthorityInMiscAuthentication(roleCodeList, (MiscAuthentication) authentication);
////        }
////        return false;
////    }
//
////    private static boolean checkAuthorityInMiscAuthentication(List<String> roleCodeList, MiscAuthentication authentication) {
////        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
////            if (roleCodeList.contains(grantedAuthority.getAuthority())) {
////                return true;
////            }
////        }
////        return false;
////    }
//
////    public static Authentication createSecureContext(AuthenticationProvider provider, String username, String password) {
////        Authentication authentication = provider.authenticate(new MiscAuthentication(username, password));
////        SecurityContextHolder.getContext().setAuthentication(authentication);
////        return authentication;
////    }
//
//    public static Authentication getAuthentication() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
//
////    public static String[] getUserAndPass() {
////        Authentication authentication = getAuthentication();
////        if (authentication != null)
////            return new String[]{authentication.getPrincipal().toString(), authentication.getCredentials().toString()};
////
////        return null;
////    }
//}
