# neis.kt

[![image](https://img.shields.io/jitpack/v/github/kimcore/neis.kt?style=flat-square)](https://github.com/kimcore/Josa.kt/releases)
[![image](https://img.shields.io/github/license/kimcore/neis.kt?style=flat-square)](https://github.com/kimcore/Josa.kt/blob/master/LICENSE)

나이스 교육정보 개방 포털의 OpenAPI를 쉽게 사용할 수 있도록 도와주는 라이브러리입니다.

## 설치
```gradle
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.kimcore", "neis.kt", "1.0")
}
```

## 사용법
### KEY 설정
[나이스 교육정보 개방 포털](https://open.neis.go.kr/portal/mainPage.do)에서 발급받은 인증키를 설정해주세요.

KEY를 설정하지 않으면 받을 수 있는 결과가 5개로 제한됩니다.
```kotlin
NEIS.KEY = "인증키"
```
### 학교 검색
List<[School](#school)>
#### 학교 이름으로 검색
```kotlin
val schoolName = "서울고등학교"

NEIS.searchSchool(schoolName)
```
#### 학교 코드로 검색
```kotlin
val officeCode = "B10"
val schoolCode = "7010083"

NEIS.searchSchoolByCode(officeCode, schoolCode)
```

### 학교 학과 정보
List<[Major](#major)>
```kotlin
val officeCode = "B10"
val schoolCode = "7010083"

NEIS.getMajors(officeCode, schoolCode)

// School 객체가 있다면
school.getMajors()
```

### 학교 급식 정보
List<[Meal](#meal)>

```kotlin
val officeCode = "B10"
val schoolCode = "7010083"

val type = MealType.ALL // 기본값
val date = LocalDate.now()
// val from = LocalDate.of(2022, 11, 21)
// val to = LocalDate.of(2022, 11, 27)

NEIS.getMeals(officeCode, schoolCode, type, date)

// School 객체가 있다면
school.getMajors(type, date)
```

### 학교 시간표 정보
List<[Timetable](#timetable)>

```kotlin
import com.github.kimcore.neis.startOfWeek
import com.github.kimcore.neis.endOfWeek

val officeCode = "B10"
val schoolCode = "7010083"
val schoolType = SchoolType.HIGH

val from = startOfWeek()
val to = endOfWeek()
val grade = 1
val classNumber = 1
// val major = "연극영화과"

NEIS.getTimetable(officeCode, schoolCode, schoolType, from, to, grade, classNumber)

// School 객체가 있다면
school.getTimetable(from, to, grade, classNumber)
```

## API

### Class

모든 클래스는 `NEISEntity` 클래스를 상속받고 있으며, `raw` 프로퍼티를 통해 원본 `JsonObject`에 접근할 수 있습니다.

#### School

```kotlin
School(
    officeCode = "B10",
    officeName = "서울특별시교육청",
    code = "7010083",
    name = "서울고등학교",
    nameEn = "Seoul High School",
    type = SchoolType.HIGH,
    location = "서울특별시 서초구 효령로 197",
    businessType = "일반계",
    homepageUrl = "http://seoul.sen.hs.kr"
)
```

#### Major

```kotlin
Major(
    officeCode = "B10",
    officeName = "서울특별시교육청",
    schoolCode = "7010083",
    schoolName = "서울고등학교",
    order = "일반계",
    name = "7차일반",
    date = "2022-11-01" // java.time.LocalDate
)
```

#### Meal

```kotlin
Meal(
    officeCode = "B10",
    officeName = "서울특별시교육청",
    schoolCode = "7010083",
    schoolName = "서울고등학교",
    type = MealType.LUNCH,
    date = "2020-07-01", // java.time.LocalDate
    content = "쌀밥...",
    origin = "쌀 : 국내산...",
    nutrition = "탄수화물(g) : 189.7...",
    calories = 1093.5
)
```

#### Timetable

```kotlin
Timetable(
    officeCode = "B10",
    officeName = "서울특별시교육청",
    schoolCode = "7010083",
    schoolName = "서울고등학교",
    year = 2022,
    semester = 2,
    date = "2022-11-28", // java.time.LocalDate
    order = "일반계",
    major = "7차일반",
    grade = 1,
    classNumber = 1,
    classroom = "1",
    period = 1,
    content = "자율활동"
)
```

### Enum

#### SchoolType

| name       | text |
|------------|------|
| UNKNOWN    | 알수없음 |
| ELEMENTARY | 초등학교 |
| MIDDLE     | 중학교  |
| HIGH       | 고등학교 |
| SPECIAL    | 특수학교 |

#### MealType

`ALL` 타입은 검색에만 사용되며, 결과에서는 반환되지 않습니다.

| name      | text |
|-----------|------|
| ALL       | 전체   |
| BREAKFAST | 조식   |
| LUNCH     | 중식   |
| DINNER    | 석식   |

