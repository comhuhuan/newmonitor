/**
 * Created by fmj on 2017/4/1.
 * version 1.0
 * 对参数进行base64加密
 */

let _keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/='

let baseUtil = params => {
  let output = ''
  let chr1, chr2, chr3, enc1, enc2, enc3, enc4
  let i = 0
  params = utf8Encode(params)
  while (i < params.length) {
    chr1 = params.charCodeAt(i++)
    chr2 = params.charCodeAt(i++)
    chr3 = params.charCodeAt(i++)
    enc1 = chr1 >> 2
    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4)
    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6)
    enc4 = chr3 & 63
    if (isNaN(chr2)) {
      enc3 = enc4 = 64
    } else if (isNaN(chr3)) {
      enc4 = 64
    }
    output = output +
            _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
            _keyStr.charAt(enc3) + _keyStr.charAt(enc4)
  }
  return output
}

// private method for UTF-8 encoding
let utf8Encode = (string) => {
  string = string.replace(/\r\n/g, '\n')
  let utftext = ''
  for (var n = 0; n < string.length; n++) {
    var c = string.charCodeAt(n)
    if (c < 128) {
      utftext += String.fromCharCode(c)
    } else if ((c > 127) && (c < 2048)) {
      utftext += String.fromCharCode((c >> 6) | 192)
      utftext += String.fromCharCode((c & 63) | 128)
    } else {
      utftext += String.fromCharCode((c >> 12) | 224)
      utftext += String.fromCharCode(((c >> 6) & 63) | 128)
      utftext += String.fromCharCode((c & 63) | 128)
    }
  }
  return utftext
}

export default baseUtil
