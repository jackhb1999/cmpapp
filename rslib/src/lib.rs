uniffi::setup_scaffolding!();

#[uniffi::export]
pub fn add(lhs: u32, rhs: u32) -> u32 {
    lhs + rhs
}
