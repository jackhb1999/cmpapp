
import * as Li9za2lrby5tanM from './skiko.mjs';
import * as QGpzLWpvZGEvY29yZQ from './@js-joda/core/dist/js-joda.js';
import * as d2FzbTpqcy1zdHJpbmc from './js-app.js-builtins.mjs';

const wasmJsTag = WebAssembly.JSTag;
const wasmTag = wasmJsTag ?? new WebAssembly.Tag({ parameters: ['externref'] });

// Placed here to give access to it from externals (js_code)
let wasmExports;
let require;

if (typeof process !== 'undefined' && process.release.name === 'node') {
    const module = await import(/* webpackIgnore: true */'node:module');
    const importMeta = import.meta;
    require = module.default.createRequire(importMeta.url);
}

export function setWasmExports(exports) {
    wasmExports = exports;
}

const _ref_Li9za2lrby5tanM_c2tpa29BcGk = Li9za2lrby5tanM.skikoApi;
const _ref_QGpzLWpvZGEvY29yZQ_ = QGpzLWpvZGEvY29yZQ;

const cachedJsObjects = new WeakMap();
function getCachedJsObject(ref, ifNotCached) {
    if (typeof ref !== 'object' && typeof ref !== 'function') return ifNotCached;
    const cached = cachedJsObjects.get(ref);
    if (cached !== void 0) return cached;
    cachedJsObjects.set(ref, ifNotCached);
    return ifNotCached;
}

const js_code = {
    'kotlin.createJsError' : (message, cause) => new Error(message, { cause }),
    'kotlin.wasm.internal.jsThrow' : wasmTag === wasmJsTag ? (e) => { throw e; } : () => {},
    'kotlin.wasm.internal.getJsEmptyString' : () => '',
    'kotlin.wasm.internal.externrefToString' : (ref) => String(ref),
    'kotlin.wasm.internal.externrefEquals' : (lhs, rhs) => lhs === rhs,
    'kotlin.wasm.internal.externrefHashCode' : 
    (() => {
    const dataView = new DataView(new ArrayBuffer(8));
    function numberHashCode(obj) {
        if ((obj | 0) === obj) {
            return obj | 0;
        } else {
            dataView.setFloat64(0, obj, true);
            return (dataView.getInt32(0, true) * 31 | 0) + dataView.getInt32(4, true) | 0;
        }
    }

    const hashCodes = new WeakMap();
    function getObjectHashCode(obj) {
        const res = hashCodes.get(obj);
        if (res === undefined) {
            const POW_2_32 = 4294967296;
            const hash = (Math.random() * POW_2_32) | 0;
            hashCodes.set(obj, hash);
            return hash;
        }
        return res;
    }

    function getStringHashCode(str) {
        var hash = 0;
        for (var i = 0; i < str.length; i++) {
            var code  = str.charCodeAt(i);
            hash  = (hash * 31 + code) | 0;
        }
        return hash;
    }

    return (obj) => {
        if (obj == null) {
            return 0;
        }
        switch (typeof obj) {
            case "object":
            case "function":
                return getObjectHashCode(obj);
            case "number":
                return numberHashCode(obj);
            case "boolean":
                return obj ? 1231 : 1237;
            default:
                return getStringHashCode(String(obj)); 
        }
    }
    })(),
    'kotlin.wasm.internal.isNullish' : (ref) => ref == null,
    'kotlin.wasm.internal.externrefToInt' : (ref) => Number(ref),
    'kotlin.wasm.internal.kotlinUIntToJsNumberUnsafe' : (x) => x >>> 0,
    'kotlin.wasm.internal.kotlinULongToJsBigIntUnsafe' : (x) => x & 0xFFFFFFFFFFFFFFFFn,
    'kotlin.wasm.internal.getCachedJsObject_$external_fun' : (p0, p1) => getCachedJsObject(p0, p1),
    'kotlin.wasm.internal.itoa32_$external_fun' : (p0) => String(p0),
    'kotlin.wasm.internal.itoa64_$external_fun' : (p0) => String(p0),
    'kotlin.wasm.internal.utoa64_$external_fun' : (p0) => String(p0),
    'kotlin.wasm.internal.utoa32_$external_fun' : (p0) => String(p0),
    'kotlin.js.jsArraySet' : (array, index, value) => { array[index] = value },
    'kotlin.js.JsArray_$external_fun' : () => new Array(),
    'kotlin.js.stackPlaceHolder_js_code' : () => (''),
    'kotlin.js.message_$external_prop_getter' : (_this) => _this.message,
    'kotlin.js.name_$external_prop_setter' : (_this, v) => _this.name = v,
    'kotlin.js.kotlinException_$external_prop_getter' : (_this) => _this.kotlinException,
    'kotlin.js.kotlinException_$external_prop_setter' : (_this, v) => _this.kotlinException = v,
    'kotlin.js.JsError_$external_class_instanceof' : (x) => x instanceof Error,
    'kotlin.random.initialSeed' : () => ((Math.random() * Math.pow(2, 32)) | 0),
    'kotlin.wasm.internal.getJsClassName' : (jsKlass) => jsKlass.name,
    'kotlin.wasm.internal.getConstructor' : (obj) => obj.constructor,
    'androidx.compose.runtime.internal.weakMap_js_code' : () => (new WeakMap()),
    'androidx.compose.runtime.internal.set_$external_fun' : (_this, p0, p1) => _this.set(p0, p1),
    'androidx.compose.runtime.internal.get_$external_fun' : (_this, p0) => _this.get(p0),
    'org.jetbrains.skia.impl.FinalizationRegistry_$external_fun' : (p0) => new FinalizationRegistry(p0),
    'org.jetbrains.skia.impl.__convertKotlinClosureToJsClosure_((Js)->Unit)' : (f) => getCachedJsObject(f, (p0) => wasmExports['__callFunction_((Js)->Unit)'](f, p0)),
    'org.jetbrains.skia.impl.register_$external_fun' : (_this, p0, p1) => _this.register(p0, p1),
    'org.jetbrains.skia.impl._releaseLocalCallbackScope_$external_fun' : () => _ref_Li9za2lrby5tanM_c2tpa29BcGk._releaseLocalCallbackScope(),
    'org.jetbrains.skiko.getNavigatorInfo' : () => navigator.userAgentData ? navigator.userAgentData.platform : navigator.platform,
    'org.jetbrains.skiko.w3c.language_$external_prop_getter' : (_this) => _this.language,
    'org.jetbrains.skiko.w3c.userAgent_$external_prop_getter' : (_this) => _this.userAgent,
    'org.jetbrains.skiko.w3c.navigator_$external_prop_getter' : (_this) => _this.navigator,
    'org.jetbrains.skiko.w3c.window_$external_object_getInstance' : () => window,
    'androidx.compose.ui.platform.warn' : (text) => { console.warn(text) },
    'androidx.compose.ui.platform.W3CTemporaryClipboard_$external_class_instanceof' : (x) => x instanceof Clipboard,
    'androidx.compose.ui.platform.W3CTemporaryClipboard_$external_class_get' : () => Clipboard,
    'androidx.compose.ui.platform.isSecureContext' : () => window.isSecureContext === true,
    'androidx.compose.ui.platform.isFullClipboardApiSupported' : () => Boolean(
            window.navigator.clipboard && 
            window.navigator.clipboard.write && 
            window.navigator.clipboard.read && 
            typeof(ClipboardItem) !== 'undefined'
            )
        ,
    'androidx.compose.ui.platform.isFallbackWriteTextApiAvailable' : () => Boolean(window.navigator.clipboard && window.navigator.clipboard.writeText),
    'androidx.compose.ui.platform.getW3CClipboard' : () => window.navigator.clipboard,
    'kotlinx.io.node.sep_$external_prop_getter' : (_this) => _this.sep,
    'kotlinx.io.node.persistModule' : 
        (globalThis.module = (typeof process !== 'undefined') && (process.release.name === 'node') ?
            await import(/* webpackIgnore: true */'node:module') : void 0, () => {})
    ,
    'kotlinx.io.node.getRequire' : () => { 
        const importMeta = import.meta;
        return globalThis.module.default.createRequire(importMeta.url);
    }
    ,
    'kotlinx.io.node.requireModule' : 
        (require, mod) => {
             try {
                 let m = require(mod);
                 if (m) return m;
                 return null;
             } catch (e) {
                 return null;
             }
        }
    ,
    'io.ktor.util.hasNodeApi' : () => 
    (typeof process !== 'undefined' 
        && process.versions != null 
        && process.versions.node != null) ||
    (typeof window !== 'undefined' 
        && typeof window.process !== 'undefined' 
        && window.process.versions != null 
        && window.process.versions.node != null)
    ,
    'io.ktor.util.logging.getKtorLogLevel' : () => process ? process.env.KTOR_LOG_LEVEL : null,
    'io.ktor.util.logging.warn_$external_fun' : (_this, p0) => _this.warn(p0),
    'io.ktor.util.logging.console_$external_prop_getter' : () => console,
    'io.ktor.network.sockets.nodejs.nodeNet' : () => eval('require')('node:net')
}

const StringConstantsProxy = new Proxy({}, {
  get(_, prop) { return prop; }
});

export { wasmTag as __TAG };

export const importObject = {
    js_code,
    intrinsics: {
        tag: wasmTag
    },
    "'": StringConstantsProxy,
    'wasm:js-string': d2FzbTpqcy1zdHJpbmc,
    './skiko.mjs': Li9za2lrby5tanM,
};
    