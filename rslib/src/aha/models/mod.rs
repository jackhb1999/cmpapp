pub mod common;
pub mod deepseek_ocr;
pub mod hunyuan_ocr;
pub mod minicpm4;

pub mod voxcpm;

use aha_openai_dive::v1::resources::chat::{
    ChatCompletionChunkResponse, ChatCompletionParameters, ChatCompletionResponse,
};
use anyhow::Result;


use crate::aha::models::{
    deepseek_ocr::generate::DeepseekOCRGenerateModel,
    hunyuan_ocr::generate::HunyuanOCRGenerateModel, minicpm4::generate::MiniCPMGenerateModel,

};

#[derive(Debug, Clone, Copy, PartialEq, Eq, clap::ValueEnum)]
pub enum WhichModel {
    #[value(name = "minicpm4-0.5b")]
    MiniCPM4_0_5B,
    #[value(name = "deepseek-ocr")]
    DeepSeekOCR,
    #[value(name = "hunyuan-ocr")]
    HunyuanOCR,
}

pub trait GenerateModel {
    fn generate(&mut self, mes: ChatCompletionParameters) -> Result<ChatCompletionResponse>;
}

pub enum ModelInstance<'a> {
    MiniCPM4(MiniCPMGenerateModel<'a>),
    DeepSeekOCR(DeepseekOCRGenerateModel),
    HunyuanOCR(HunyuanOCRGenerateModel<'a>),
}

impl<'a> GenerateModel for ModelInstance<'a> {
    fn generate(&mut self, mes: ChatCompletionParameters) -> Result<ChatCompletionResponse> {
        match self {
            ModelInstance::MiniCPM4(model) => model.generate(mes),
            ModelInstance::DeepSeekOCR(model) => model.generate(mes),
            ModelInstance::HunyuanOCR(model) => model.generate(mes),
        }
    }

}

pub fn load_model(model_type: WhichModel, path: &str) -> Result<ModelInstance<'_>> {
    let model = match model_type {
        WhichModel::MiniCPM4_0_5B => {
            let model = MiniCPMGenerateModel::init(path, None, None)?;
            ModelInstance::MiniCPM4(model)
        }
        WhichModel::DeepSeekOCR => {
            let model = DeepseekOCRGenerateModel::init(path, None, None)?;
            ModelInstance::DeepSeekOCR(model)
        }
        WhichModel::HunyuanOCR => {
            let model = HunyuanOCRGenerateModel::init(path, None, None)?;
            ModelInstance::HunyuanOCR(model)
        }
    };
    Ok(model)
}
