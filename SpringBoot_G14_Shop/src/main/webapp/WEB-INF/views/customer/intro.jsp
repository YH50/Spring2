<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>

<div>
    <%@include file="sub_image_menu.jsp"%>
        <article style="display: flex; flex-direction: column">
            <h2>회사 소개</h2>
            동해물과 백두산이 마르고 닳도록 블라블라블라...

            <h2>오시는 길</h2>
            서울특별시 송파구 올림픽로 25<br/>
            전화 : 02 - 1994 - 2023<br>
            FAX : 02 - 2016 - 2019</br>

            <h3>전 철</h3>
            2호선, 9호선 종합운동장역

            <h3>버 스</h3>
            이건 알아서들 찾아서 오세요 아ㅋㅋ

            <h3>위치 안내</h3>
            <!-- * 카카오맵 - 지도퍼가기 -->
            <!-- 1. 지도 노드 -->
            <div id="daumRoughmapContainer1719190921714" class="root_daum_roughmap root_daum_roughmap_landing"></div>

            <!--
                2. 설치 스크립트
                * 지도 퍼가기 서비스를 2개 이상 넣을 경우, 설치 스크립트는 하나만 삽입합니다.
            -->
            <script charset="UTF-8" class="daum_roughmap_loader_script" src="https://ssl.daumcdn.net/dmaps/map_js_init/roughmapLoader.js"></script>

            <!-- 3. 실행 스크립트 -->
            <script charset="UTF-8">
                new daum.roughmap.Lander({
                    "timestamp" : "1719190921714",
                    "key" : "2jrxh",
                    "mapWidth" : "640",
                    "mapHeight" : "360"
                }).render();
            </script>
        </article>
    </div>
</section>



<%@ include file="../include/footer.jsp" %>