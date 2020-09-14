# WorkManager
연기할 수 있는 작업을 위해 WorkManager를 이용한다. 가장 중요한 것은, 앱이 종료되더라도 시스템이 연기된 작업을 실행할 것이라는 것을 보증한다는 것이다.
사용자가 앱에서 벗어나더라도 다양한 상황에서 작업을 시작하기 위해 WorkManager가 logic를 care한다. WorkManager 이전에 Firebase JobDispatcher, Job Scheduler and alarm 매니저와 
BroadCastReceiver를 사용하여 작업을 스케쥴링했다. 우리는 많은 제약과 요구사항을 처리하기 위해 코드를 사용해야 했다.
하지만 지금은 WorkManager가 있다. 우리가 해야할 일은 그저 작업을 넘겨주는 것 뿐이다. 요구사항을 고려하여 어떤 스케쥴러를 사용할지 결정할 것이고, 제약조건을 처리할 것이다.
```
API23 미만이라면, WorkManager는 BroadCastReceiver와 AlarmManager를 조합하여 사용한다. 
API23 이상이라면, WorkManager가 JobScheduler를 선택한다. 
```
만약에 Firebase를 사용한다면, WorkManager는 Firebase JobDispatcher를 선택할 것이다. WorkManager는 많은 고급 기능들을 제공하며, 상태 업데이트를 LiveData로서 제공한다.
그것은 우리가 작업이 언제 실행되는지 제약할 수 있게 해주며, WorkManager는 배터리 전력과 같은 시스템 자원을 덜 사용한다. 다른 안드로이드 버전과의 호환성을 취급하며, 비동기식 작업을 지원한다.
정기적인 작업들(periodic tasks)을 지원하는 경우, WorkManager는 background 스레드에서 실행되어야하지만, 프로세스 종료에서 살아남을 필요가 없는 작업을 위한 것이 아니다.
WorkManager가 필요없는 백그라운드 작업의 경우, 코루틴이나 rxjava를 사용할 수 있다.
우리는 WorkManager를 활용하여 작업을 스케쥴링할때 정기적인 작업(periodic work) 요청 및 일회성 작업(one time work)요청 두가지 유형의 작업요청을 볼 수 있다. 
```
영업팀을 위해 만들어진 앱이 있다고 가정해 봅시다. 각 판매원은 제품의 현재 재고 수를 볼 필요가 있다. 그러므로 우리의 앱은 메인 서버에서 업데이트 되어야 한다. 
따라서 이 경우 재고 세부 정보를 반복적으로 업데이트하는 것은 작업이며, WorkManager와 함께 설정할 수 있다.
이 작업을 반복해서 실행하면, 약 30분 정도의 시간이 소요된다고 한다면, 그러한 유형의 업무 요청은 주기적인 업무(periodic work) 요청이라고 불린다.

조사팀을 위해 만들어진 앱이 있다고 가정한다면, 각 구성원은 이미지를 포함한 새로운 레코드를 삽입하고 저장 버튼을 눌러 메인 웹 서버로 업로드 할 것이다.
따라서 이 경우 서비스 세부 정보를 업로드하는 것이 과제이다. WorkManager의 지원을 통해 인터넷의 가용성과 장치의 배터리 충전 수준을 고려하여 
대략적인 시간에 이 작업이 실행되도록 예약할 수 있다. 이러한 유형의 작업 요청을 일회성 작업(one time work)이라고 한다. 
```


