const KEY = 'monitor_id'

class Store {
  constructor () {
    this.store = window.localStorage
    this.prefix = KEY
  }
  set (key, value, fn) {
    try {
      value = JSON.stringify(value)
    } catch (e) {
      value = value
    }
    this.store.setItem(this.prefix + key, value)

    fn && fn()
  }
  get (key, fn) {
    if (!key) {
      throw new Error('沒有找到key!')
    }
    if (typeof key === 'object') {
      throw new Error('key不能是一个对象')
    }
    let value = this.store.getItem(this.prefix + key)
    if (value) {
      try {
        value = JSON.parse(value)
      } catch (e) {
        value = value
      }
      fn && fn()
      return value
    }
  }
  remove (key) {
    if (toString.call(key) == '[object Array]') {
      key.forEach((item, index) => {
        this.store.removeItem(this.prefix + item)
      })
    } else if (toString.call(key) == '[object String]') {
      this.store.removeItem(this.prefix + key)
    }
  }
}

export default new Store()
