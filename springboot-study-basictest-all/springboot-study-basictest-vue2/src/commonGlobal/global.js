// [global > global.js 설명]
// 1. 모든 컴포넌트가 공유할 수 있는 글로벌 영역 입니다
// [설정 수행 실시]
///*


export default {

    install(Vue) {
        Vue.prototype.$main = {
            loading: null
        };
    }
}
//*/