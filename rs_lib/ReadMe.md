
进行打包及桥接代码生成

cargo b --release

uniffi-bindgen-java generate --out-dir ./generated-java --library ./target/release/rslib.lib