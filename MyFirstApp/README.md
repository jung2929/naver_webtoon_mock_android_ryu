# naver_webtoon_mock_android_ryu
네이버웹툰 안드로이드 앱 만들어보기

MyFirstApp
|- app
    - build
    - libs
    - src # 소스
        - main/java/com.example.myfirstapp
            - baseClass
                - BaseWebtoonData.java
                - ResponseBaseData.java # 서버로부터 받아오는 기본적인 response
            - Common
                - Adapter
                    -WebtoonListAdapter.java # WebtoonData를 뿌려주는 Adapter
                - Entities
                    - WebtoonData.java
            - Login # 로그인
                - Entities
                    - ResponseLoginData.java
                - LoginActivity.java
            - Main # 스플래시 이후 첫 화면
                - Adapter
                    - MainPagerAdapter.java # 메인화면 탭 (웹툰, 베스트도전, PLAY, MY, 설정) PagerAdapter
                    - MyTabPagerAdapter.java # MY탭 (관심웹툰, 결제한 내역) PagerAdapter
                    - WebtoonDaysPageAdapter.java # 요일별 웹툰 (월, 화, 수, ... , 신작, 완결) PagerAdapter
                - Entities
                    - MenuTabItem.java # 메인화면 탭의 정보
                    - ResponseMyWebtoonListData.java # 서버로부터 받아오는 관심웹툰 정보
                    - ResponseWebtoonListData.java # 서버로부터 받아오는 웹툰 정보
                - Fragment
                    - MainWebtoonTabFragment.java # 메인화면 탭의 '웹툰'탭 담당
                    - MyTabFragment.java # 메인화면 탭의 'MY'탭 담당
                    - SettingTabFragment.java # 메인화면 탭의 '설정'탭 담당
                - MainActivity.java
            - MemberInformation # 로그인한 상태에서 볼 수 있는 로그인 정보
                - Entities
                    - RequestWithdrawalData.java # 서버에서 회원탈퇴하는 데에 요구하는 정보
                    - ResponseWithdrawalData.java # 서버에서 받아오는 회원탈퇴 응답
                - MemberInformationActivity.java
            - SignUp # 회원가입
                - Entities
                    - RequestSignUpData.java # 서버에서 회원가입하는 데에 요구하는 정보
                    - ResponseSignUpData.java # 서버에서 받아오는 회원가입 응답
                - SignUpActivity.java
            - Splash # 스플래시
                -SplashActivity.java
            - Login # 로그인
                - Adapter #
                - Activity #
|
|
|
|