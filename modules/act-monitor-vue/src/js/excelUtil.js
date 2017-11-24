/**
 * Created by fmj on 2017/4/12.
 *
 * 发送请求到后端,浏览器直接下载文件
 */

require('script-loader!file-saver')
// require('script-loader!./blobUtil');
require('script-loader!xlsx/dist/xlsx.core.min')

function createQuery (form, params) {
  for (const k in params) {
    const input = document.createElement('input')
    input.type = 'hidden'
    input.name = k
    input.value = params[k]
    form.appendChild(input)
  }
}

function downloadFile (url, params) {
  const form = document.createElement('form')
  form.style.display = 'none'
  form.action = url
  form.id = 'myForm'
  createQuery(form, params)
  document.body.appendChild(form)
  form.submit()
}

function Workbook () {
  if (!(this instanceof Workbook)) return new Workbook()
  this.SheetNames = []
  this.Sheets = {}
}

function datenum (v, date1904) {
  if (date1904) v += 1462
  const epoch = Date.parse(v)
  return (epoch - new Date(Date.UTC(1899, 11, 30))) / (24 * 60 * 60 * 1000)
}

function sheet_from_array_of_arrays (data) {
  const ws = {}
  const range = {s: {c: 10000000, r: 10000000}, e: {c: 0, r: 0}}
  for (let R = 0; R !== data.length; ++R) {
    for (let C = 0; C !== data[R].length; ++C) {
      if (range.s.r > R) range.s.r = R
      if (range.s.c > C) range.s.c = C
      if (range.e.r < R) range.e.r = R
      if (range.e.c < C) range.e.c = C
      const cell = {v: data[R][C]}
      if (cell.v == null) continue
      const cell_ref = XLSX.utils.encode_cell({c: C, r: R})

      if (typeof cell.v === 'number') cell.t = 'n'
      else if (typeof cell.v === 'boolean') cell.t = 'b'
      else if (cell.v instanceof Date) {
        cell.t = 'n'
        cell.z = XLSX.SSF._table[14]
        cell.v = datenum(cell.v)
      } else cell.t = 's'

      ws[cell_ref] = cell
    }
  }
  if (range.s.c < 10000000) ws['!ref'] = XLSX.utils.encode_range(range)
  return ws
}

function s2ab (s) {
  const buf = new ArrayBuffer(s.length)
  const view = new Uint8Array(buf)
  for (let i = 0; i !== s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF
  return buf
}

function export_json_to_excel (th, jsonData, defaultTitle) {
  /* original data */

  const data = jsonData
  data.unshift(th)
  const ws_name = 'SheetJS'

  const wb = new Workbook(), ws = sheet_from_array_of_arrays(data)

  /* add worksheet to workbook */
  wb.SheetNames.push(ws_name)
  wb.Sheets[ws_name] = ws

  const wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: false, type: 'binary'})
  const title = defaultTitle || '列表'
  saveAs(new Blob([s2ab(wbout)], {type: 'application/octet-stream'}), title + '.xlsx')
}

/**
 * TODO 请求失败时 页面内报错 建议使用exportJsonExcel的方法导出
 * @param url   导出excel,请求路径
 * @param params  导出查询参数
 */
export const exportDataExcel = (url, params) => {
  downloadFile(url, params)
}

/**
 * Created by fmj on 2017/4/12.
 * Update by fmj on 2017/08/28. 前端导出Excel
 * 通过JsonExportExcel.min.js
 * 处理后端返回的json数据导出excel
 *
 *
 * @param header
 * @param data
 * @param fileName
 */
export const exportJsonExcel = (header, exportData, fileName) => {
  export_json_to_excel(header, exportData, fileName)
}
