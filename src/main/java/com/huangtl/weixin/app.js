//app.js
const config = require("config.js")
// const worker = wx.createWorker('workers/request/index.js') // 文件名指定 worker 的入口文件路径，绝对路径

App({
  onLaunch: function () {

    console.log(config)

    const _this = this
    // 加载字典表
    _this.getDict()

    //1.检测登录状态是否过期 wx.checkSession(Object object)
    wx.checkSession({
      success(res) {
        //session_key 未过期，并且在本生命周期一直有效
        console.log("checkSession:session_key 未过期，并且在本生命周期一直有效")
    

        let unionid = wx.getStorageSync('unionid')
        console.log('getstorage unionid :' + unionid)
        if (unionid) {
          _this.globalData.unionid = unionid
          _this.getServiceOperatorInfo()
        }

        let openid = wx.getStorageSync('openid')
        console.log('getstorage openid :' + openid)
        if (openid) {
          _this.globalData.openid = openid
        }

        //获取是否授权，处理
        _this.getUserInfoSetting()
      },
      fail() {
        // session_key 已经失效，需要重新执行登录流程
        console.log("checkSession:session_key 已经失效，需要重新执行登录流程")
        // 2.登录状态过期的话重新登录
        _this.wxLogin();
      }
    })
    
    
    
  },
  wxLogin(){
    const _this = this
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        wx.request({
          url: config.miniPath + '/code2Session',
          data: { code: res.code },
          success: res2 => {
            let resData = res2.data
            let code2Session = resData.obj.code2Session
            _this.globalData.openid = code2Session.openid;
            // _this.globalData.session_key = code2Session.session_key;
            _this.globalData.unionid = code2Session.unionid;
            wx.setStorage({ key: "openid", data: code2Session.openid })
            // wx.setStorage({ key: "session_key", data: code2Session.session_key})
            wx.setStorage({ key: "unionid", data: code2Session.unionid })
            console.log("openid:" + code2Session.openid)
            // console.log("session_key:" + code2Session.session_key)
            console.log("unionid:" + code2Session.unionid)
            if (resData.obj.weixinServiceOperator) {
              //已绑定
              wx.setStorage({ key: "serviceOperator", data: resData.obj.weixinServiceOperator.serviceOperator })
              _this.globalData.serviceOperator = resData.obj.weixinServiceOperator.serviceOperator
              _this.globalData.userInfo = resData.obj.weixinServiceOperator.weixinUserInfo
              _this.goIndex()
            }

            //获取是否授权，处理
            _this.getUserInfoSetting()

            if (resData.success) {
            } else {
              console.log(resData.msg)
            }
          }
        })
      },
      fail: res => {
        console.log("code2Session fail")
      }
    })
  },
  getUserInfoSetting(){
    var _this =this
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          console.log(' 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框')
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              _this.globalData.userInfo = res.userInfo
              _this.globalData.miniUserInfoEncryptedData = res;
              console.log('wx.getUserInfo=>res', res)
              if (!_this.globalData.unionid) {
                console.log('_this.globalData.unionid为空')
                _this.loadUnionIdCallBack = function () {
                  console.log('wx.getSetting自动getUserInfo-》loadUnionIdCallBack')
                  _this.getServiceOperatorInfo()
                }
                _this.loadUnionId()
              }
            }
          })
        }
      }
    })
  },
  /**
   * 根据wx:getuserinfo内容和openid加载获取unionid
   */
  loadUnionId() {
    const _this = this
    _this.globalData.miniUserInfoEncryptedData.openid = _this.globalData.openid
    wx.request({
      url: config.miniPath + '/userInfoByEncryptedData',
      data: _this.globalData.miniUserInfoEncryptedData,
      success: res => {
        console.log('解密获取unionid:', res)
        let data = res.data;
        if (data.success) {
          _this.globalData.unionid = data.obj.unionId
          _this.globalData.openid = data.obj.openId;
          wx.setStorage({ key: "openid", data: data.obj.openId })
          wx.setStorage({ key: "unionid", data: data.obj.unionId })
          //回调函数
          if (_this.loadUnionIdCallBack){
            _this.loadUnionIdCallBack()
          }
        } else {
          console.log(data.msg)
          if (data.code == -2) {
            // sessionKey为空,重新登录一次
            _this.wxLogin();
          }
        }
      }
    })
  },
  globalData: {
    miniUserInfoEncryptedData:null,
    userInfo: null,
    openid: null,
    unionid: null,
    session_key: null,
    serviceOperator: null,
    dict: { 
      dictServiceProject:{},//服务项目
      dictServiceProjectArr:[],//服务项目
      dictOperatorType:{},//服务商类别
      dictOperatorTypeArr:[],//服务商类别
      dictOperatorServiceType:{}, //服务商服务类别
      dictOperatorServiceTypeArr:{}, //服务商服务类别
    }
  },
  goIndex(){
    wx.switchTab({
      url: '../index/index',
    })
  },
  getServiceOperatorInfo(){
    const _this = this
    wx.showLoading({ title: "加载中..." })
    wx.request({
      url: config.miniPath + '/getServiceOperatorInfo',
      data: { unionid: _this.globalData.unionid },
      success: function (res) {
        wx.hideLoading()
        let data = res.data
        if (data.success) {
          _this.globalData.serviceOperator = data.obj.serviceOperator
          _this.globalData.userInfo = data.obj.weixinUserInfo
          _this.globalData.unionid = data.obj.weixinUserInfo.unionid
          _this.goIndex()
        }
      }
    })
  },
  getDict() {
    const _this = this
    wx.request({
      url: config.miniPath + '/getDict',
      data: {  },
      success: function (res) {
        let data = res.data
        if (data.success) {
          _this.globalData.dict.dictServiceProject = data.obj.dictServiceProject
          _this.globalData.dict.dictServiceProjectArr = data.obj.dictServiceProjectArr
          _this.globalData.dict.dictOperatorType = data.obj.dictOperatorType
          _this.globalData.dict.dictOperatorTypeArr = data.obj.dictOperatorTypeArr
          _this.globalData.dict.dictOperatorServiceType = data.obj.dictOperatorServiceType
          _this.globalData.dict.dictOperatorServiceTypeArr = data.obj.dictOperatorServiceTypeArr
        }
      }
    })
  }
})