extern crate libc;
extern crate chrono;

use libc::c_char;
use std::ffi::CStr;
use std::ffi::CString;
use chrono::*;

#[no_mangle]
pub extern fn fibonacci(x: i32) -> i32 {
  if x <= 2 {
    return 1;
  } else {
    return fibonacci(x - 1) + fibonacci(x - 2);
  }
}

#[no_mangle]
pub extern fn process(p: *const c_char) -> *const c_char {
    let c_str = unsafe {
        assert!(!p.is_null());
        CStr::from_ptr(p)
    };
    let mut r_str = String::from_utf8(Vec::from(c_str.to_bytes())).unwrap();
    let local: DateTime<Local> = Local::now();
    r_str = r_str + &local.format("%Y-%m-%d %H:%M:%S").to_string();
    CString::new(r_str).unwrap().into_raw()
}