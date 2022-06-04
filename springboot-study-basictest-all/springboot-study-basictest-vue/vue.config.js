const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true, 
  //외부 모듈(node_modules 등)이 babel-loader에 의해 변환이 안되는 걸 방지
  // https://cli.vuejs.org/config/#runtimecompiler는 transpileDependencies를 참조
  devServer: {
    // proxy: {
    //   "^/api": {                            //  ^/api  정규식 ^는 시작을 나타냄 ^/api는 /api로 시작 하는 것을 의미
    //     pathRewrite: { "^/api": "/api" },   //  pathRewrite: { "^/api": "/api" }   ^/api를  /api로 다시 쓰겠다.
    //     changeOrigin: true,                 //  cors를 막기 위한 설정
    //     logLevel: "debug",                   //  debug 수준의 오류 로그를 남긴다.
    //     target: "http://localhost:8080",
    //   }
    // }
    proxy: {
      "^/": {                            //  ^/api  정규식 ^는 시작을 나타냄 ^/api는 /api로 시작 하는 것을 의미
        pathRewrite: { "^/": "/" },   //  pathRewrite: { "^/": "/" }   ^/api를  /api로 다시 쓰겠다.
        changeOrigin: true,                 //  cors를 막기 위한 설정
        logLevel: "debug",                   //  debug 수준의 오류 로그를 남긴다.
        ws: false,
        //'ws://localhost:8080/ api /handler' 에 대한 WebSocket 연결 실패: 잘못된 프레임 헤더
        //프로젝트에 대한 해결책은 vue.config.js 프록시에서 ws:true를 false로 변경하는 것입니다.
        target: "http://localhost:8080",
      }
    }
  }
})


// module.exports = {
//   devServer: {
//     proxy: {
//       "^/api": { 
//         target: "http://localhost:8080",
//         pathRewrite: { "^/api": "/api" },
//         changeOrigin: true,
//         logLevel: "debug"
//       }
//     }
//   }
// };