
const MINH = 450 // 最小高度
const GAP = 180 // 距离顶部与底部所留的间隙高度(包括tabs的高度)

/**
 * 求出自适应的content-container高度
 * @return {[number]} 算出的高度
 */
export const CH = () => {
  let h = document.documentElement.clientHeight
  h = parseInt(h) - GAP
  h = h > MINH ? h : MINH
  return h
}
