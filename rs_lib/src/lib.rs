pub mod aha;

use crate::aha::models::GenerateModel;
use crate::aha::models::hunyuan_ocr::generate::HunyuanOCRGenerateModel;
use aha_openai_dive::v1::resources::chat::ChatCompletionParameters;
use lazy_static::lazy_static;
use std::sync::Mutex;
use tokio::time::Instant;

uniffi::setup_scaffolding!();

#[uniffi::export]
pub fn add(lhs: u32, rhs: u32) -> u32 {
    lhs + rhs
}

#[test]
pub fn test() {
    let start = Instant::now();
    let string =
        hunyuan_ocr_generate("file:///D:/kotlin/cmpapp/rslib/assets/img/ocr_test1.png".to_string());
    let string = hunyuan_ocr_generate("file:///C:/Users/jackH/Desktop/tensor.png".to_string());
    let duration = start.elapsed();
    println!("Time elapsed in load model is: {:?}", duration);
}

// 1. 定义全局共享变量
lazy_static! {
    static ref SHARED_DATA: Mutex<HunyuanOCRGenerateModel<'static>> = Mutex::new(
        HunyuanOCRGenerateModel::init(
            "C:\\Users\\jackH\\.cache\\modelscope\\hub\\Tencent-Hunyuan\\HunyuanOCR",
            None,
            None
        )
        .unwrap()
    );
}

#[uniffi::export]
pub fn hunyuan_ocr_generate(url: String) -> String {
    // RUST_BACKTRACE=1 cargo test -F cuda hunyuan_ocr_generate -r -- --nocapture
    let message = format!(
        r#"
    {{
        "model": "hunyuan-ocr",
        "messages": [
            {{
                "role": "user",
                "content": [
                    {{
                        "type": "image",
                        "image_url":
                        {{
                            "url": "{}"
                        }}
                    }},
                    {{
                        "type": "text",
                        "text": "提取文档图片中正文的所有信息用markdown格式表示，其中页眉、页脚部分忽略，表格用html格式表达，文档中公式用latex格式表示，按照阅读顺序组织进行解析。"
                    }}
                ]
            }}
        ]
    }}
    "#,
        url
    );

    let mes: ChatCompletionParameters = serde_json::from_str(&message).unwrap();
    let i_start = Instant::now();
    let mut model = SHARED_DATA.lock().unwrap();
    let i_duration = i_start.elapsed();
    println!("Time elapsed in load model is: {:?}", i_duration);
    let i_start = Instant::now();
    let res = model.generate(mes).unwrap();
    let i_duration = i_start.elapsed();
    println!("Time elapsed in generate is: {:?}", i_duration);
    println!("generate: \n {:?}", res);
    res.choices[0].message.text().unwrap().to_string()
    // res.object
}
