extern crate libc;

use libc::{c_char};
use std::ffi::CStr;
use std::ffi::CString;

#[no_mangle]
pub extern fn fibonacci(x: i32) -> i32 {
  if x <= 2 {
    return 1;
  } else {
    return fibonacci(x - 1) + fibonacci(x - 2);
  }
}

#[no_mangle]
pub extern fn process(s: *const c_char) -> *const c_char {
    let c_str = unsafe {
        assert!(!s.is_null());
        CStr::from_ptr(s)
    };
    let mut r_str = String::from_utf8(Vec::from(c_str.to_bytes())).unwrap();
    r_str = r_str + "_test";
    CString::new(r_str).unwrap().into_raw()
}