# Generated by CMake 3.6.2

if("${CMAKE_MAJOR_VERSION}.${CMAKE_MINOR_VERSION}" LESS 2.5)
   message(FATAL_ERROR "CMake >= 2.6.0 required")
endif()
cmake_policy(PUSH)
cmake_policy(VERSION 2.6)
#----------------------------------------------------------------
# Generated CMake target import file.
#----------------------------------------------------------------

# Commands may need to know the format version.
set(CMAKE_IMPORT_FILE_VERSION 1)

# Protect against multiple inclusion, which would fail when already imported targets are added once more.
set(_targetsDefined)
set(_targetsNotDefined)
set(_expectedTargets)
foreach(_expectedTarget mediastreamer_base mediastreamer_voip)
  list(APPEND _expectedTargets ${_expectedTarget})
  if(NOT TARGET ${_expectedTarget})
    list(APPEND _targetsNotDefined ${_expectedTarget})
  endif()
  if(TARGET ${_expectedTarget})
    list(APPEND _targetsDefined ${_expectedTarget})
  endif()
endforeach()
if("${_targetsDefined}" STREQUAL "${_expectedTargets}")
  unset(_targetsDefined)
  unset(_targetsNotDefined)
  unset(_expectedTargets)
  set(CMAKE_IMPORT_FILE_VERSION)
  cmake_policy(POP)
  return()
endif()
if(NOT "${_targetsDefined}" STREQUAL "")
  message(FATAL_ERROR "Some (but not all) targets in this export set were already defined.\nTargets Defined: ${_targetsDefined}\nTargets not yet defined: ${_targetsNotDefined}\n")
endif()
unset(_targetsDefined)
unset(_targetsNotDefined)
unset(_expectedTargets)


# Compute the installation prefix relative to this file.
get_filename_component(_IMPORT_PREFIX "${CMAKE_CURRENT_LIST_FILE}" PATH)
get_filename_component(_IMPORT_PREFIX "${_IMPORT_PREFIX}" PATH)
get_filename_component(_IMPORT_PREFIX "${_IMPORT_PREFIX}" PATH)
get_filename_component(_IMPORT_PREFIX "${_IMPORT_PREFIX}" PATH)

# Create imported target mediastreamer_base
add_library(mediastreamer_base SHARED IMPORTED)

set_target_properties(mediastreamer_base PROPERTIES
  INTERFACE_LINK_LIBRARIES "/Users/macmini02/Library/Android/sdk/ndk-bundle/sources/cxx-stl/gnu-libstdc++/4.9/libs/x86/libgnustl_shared.so;log;atomic;-no-canonical-prefixes;-Wl,--no-undefined;-Wl,-z,noexecstack;-Wl,-z,relro;-Wl,-z,now;/Users/macmini02/Library/Android/sdk/ndk-bundle/sources/cxx-stl/gnu-libstdc++/4.9/libs/x86/libgnustl_shared.so;log;atomic;-no-canonical-prefixes;-Wl,--no-undefined;-Wl,-z,noexecstack;-Wl,-z,relro;-Wl,-z,now"
)

# Create imported target mediastreamer_voip
add_library(mediastreamer_voip SHARED IMPORTED)

set_target_properties(mediastreamer_voip PROPERTIES
  INTERFACE_LINK_LIBRARIES "/Users/macmini02/Library/Android/sdk/ndk-bundle/sources/cxx-stl/gnu-libstdc++/4.9/libs/x86/libgnustl_shared.so;log;atomic;-no-canonical-prefixes;-Wl,--no-undefined;-Wl,-z,noexecstack;-Wl,-z,relro;-Wl,-z,now;/Users/macmini02/Library/Android/sdk/ndk-bundle/sources/cxx-stl/gnu-libstdc++/4.9/libs/x86/libgnustl_shared.so;log;atomic;-no-canonical-prefixes;-Wl,--no-undefined;-Wl,-z,noexecstack;-Wl,-z,relro;-Wl,-z,now;mediastreamer_base;bctoolbox;ortp;/Users/macmini02/Library/Android/sdk/ndk-bundle/platforms/android-14/arch-x86/usr/lib/libm.so;/Users/macmini02/Documents/AndroidStudioProjects/linphonean/linphone-android/liblinphone-sdk/android-x86/lib/libcpufeatures.a;/Users/macmini02/Documents/AndroidStudioProjects/linphonean/linphone-android/liblinphone-sdk/android-x86/lib/libsupport.a;GLESv2;dl;bzrtp-static;/Users/macmini02/Documents/AndroidStudioProjects/linphonean/linphone-android/liblinphone-sdk/android-x86/lib/libsrtp.a;/Users/macmini02/Documents/AndroidStudioProjects/linphonean/linphone-android/liblinphone-sdk/android-x86/lib/libgsm.a;/Users/macmini02/Documents/AndroidStudioProjects/linphonean/linphone-android/liblinphone-sdk/android-x86/lib/libopus.a;/Users/macmini02/Library/Android/sdk/ndk-bundle/platforms/android-14/arch-x86/usr/lib/libm.so;/Users/macmini02/Documents/AndroidStudioProjects/linphonean/linphone-android/liblinphone-sdk/android-x86/lib/libspeex.a;/Users/macmini02/Documents/AndroidStudioProjects/linphonean/linphone-android/liblinphone-sdk/android-x86/lib/libspeexdsp.a;/Users/macmini02/Documents/AndroidStudioProjects/linphonean/linphone-android/liblinphone-sdk/android-x86/lib/libffmpeg-linphone-x86.so;/Users/macmini02/Documents/AndroidStudioProjects/linphonean/linphone-android/liblinphone-sdk/android-x86/lib/libvpx.a;matroska2-static"
)

if(CMAKE_VERSION VERSION_LESS 2.8.12)
  message(FATAL_ERROR "This file relies on consumers using CMake 2.8.12 or greater.")
endif()

# Load information for each installed configuration.
get_filename_component(_DIR "${CMAKE_CURRENT_LIST_FILE}" PATH)
file(GLOB CONFIG_FILES "${_DIR}/Mediastreamer2Targets-*.cmake")
foreach(f ${CONFIG_FILES})
  include(${f})
endforeach()

# Cleanup temporary variables.
set(_IMPORT_PREFIX)

# Loop over all imported files and verify that they actually exist
foreach(target ${_IMPORT_CHECK_TARGETS} )
  foreach(file ${_IMPORT_CHECK_FILES_FOR_${target}} )
    if(NOT EXISTS "${file}" )
      message(FATAL_ERROR "The imported target \"${target}\" references the file
   \"${file}\"
but this file does not exist.  Possible reasons include:
* The file was deleted, renamed, or moved to another location.
* An install or uninstall procedure did not complete successfully.
* The installation package was faulty and contained
   \"${CMAKE_CURRENT_LIST_FILE}\"
but not all the files it references.
")
    endif()
  endforeach()
  unset(_IMPORT_CHECK_FILES_FOR_${target})
endforeach()
unset(_IMPORT_CHECK_TARGETS)

# This file does not depend on other imported targets which have
# been exported from the same project but in a separate export set.

# Commands beyond this point should not need to know the version.
set(CMAKE_IMPORT_FILE_VERSION)
cmake_policy(POP)
