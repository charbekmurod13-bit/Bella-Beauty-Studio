package com.example.data.mock

import com.example.R
import com.example.data.models.Appointment
import com.example.data.models.AppointmentStatus
import com.example.data.models.BookingDayOption
import com.example.data.models.GalleryItem
import com.example.data.models.Review
import com.example.data.models.Service
import com.example.data.models.ServiceCategory
import com.example.data.models.Specialist
import com.example.data.models.User

object BellaMockData {

    val defaultUser = User(
        id = "user_amelia",
        fullName = "Amelia Bennett",
        email = "amelia.bennett@email.com",
        phone = "+998 90 123 45 67",
        avatarResId = R.drawable.img_beauty_portrait_1795536624478,
        loyaltyTitle = "BELLA SODIQLIK DASTURI",
        loyaltyMessage = "Sadoqatingizga mehrimiz cheksiz.",
        notificationsEnabled = true,
        reminder24hEnabled = true
    )

    val services: List<Service> = listOf(
        Service(
            id = "srv_facial",
            name = "Yuzni nurlantirish",
            shortDescription = "Tozalash, namlash va nur baxsh etish",
            favoriteTagline = "Namlik va qo‘shimcha jilo.",
            fullDescription = "Yuz terisini chuqur tozalash, limfodrenaj massaj hamda tabiiy gialuron zardoblari bilan intensiv namlash muolajasi. Teri rangini tekislab, sog‘lom va tabiiy porlashni tiklaydi.",
            category = ServiceCategory.FACIAL,
            durationMinutes = 60,
            priceUsd = 85,
            rating = 4.9,
            reviewCount = 128,
            imageResId = R.drawable.img_facial_glow_1795536605943,
            benefits = listOf(
                "Chuqur va mayin tozalash",
                "Botanik namlovchi niqob",
                "Yuz mushaklarini bo‘shashtiruvchi massaj",
                "SPF bilan yakuniy himoya"
            ),
            preparationNote = "Muolajadan oldin yengil tozalash tavsiya etiladi. Barcha parvarish vositalari studiyada taqdim etiladi.",
            isFeaturedOnHome = true
        ),
        Service(
            id = "srv_hair",
            name = "Sochga jilo va turmak",
            shortDescription = "Ipakdek mayin, jilokor sochlar.",
            favoriteTagline = "Har kuni oson, go‘zal soch turmagi.",
            fullDescription = "Argan va ipak oqsillari asosidagi oziqlantiruvchi parvarish bilan birgalikda tabiiy to‘lqinli yoki silliq kundalik va bayramona soch turmagi.",
            category = ServiceCategory.HAIR,
            durationMinutes = 45,
            priceUsd = 65,
            rating = 4.8,
            reviewCount = 94,
            imageResId = R.drawable.img_hair_waves_1795536605660,
            benefits = listOf(
                "Issiqlikdan himoyalovchi zardob",
                "Tabiiy hajm va yorqin jilo",
                "Soch turiga mos individual shakl",
                "Uzoq saqlanuvchi yengil fiksatsiya"
            ),
            preparationNote = "Sochingizning tabiiy tuzilishiga ko‘ra usta bilan maslahatlashib turmak tanlanadi.",
            isFeaturedOnHome = true
        ),
        Service(
            id = "srv_manicure",
            name = "Tabiiy manikyur-pedikyur",
            shortDescription = "Nafis bo‘yalgan, ozoda tirnoqlar",
            favoriteTagline = "Har bir jihati mukammal va nafis.",
            fullDescription = "Nozik apparat va klassik ishlov, kutikulani yumshoq parvarishlash, vitaminli yog‘ bilan oziqlantirish va tabiiy nude yoki shaffof jilo qoplamasi.",
            category = ServiceCategory.NAILS,
            durationMinutes = 75,
            priceUsd = 70,
            rating = 4.9,
            reviewCount = 112,
            imageResId = R.drawable.img_natural_manicure_1795536623595,
            benefits = listOf(
                "Steril asboblar va xavfsiz jarayon",
                "Qo‘l va oyoq terisi uchun namlovchi krem",
                "Mustahkamlovchi baza qoplamasi",
                "Nozik tabiiy ranglar palitrasi"
            ),
            preparationNote = "Oldingi qoplamani yechish kerak bo‘lsa, qo‘shimcha 15 daqiqa vaqt ajratiladi.",
            isFeaturedOnHome = false
        ),
        Service(
            id = "srv_makeup",
            name = "Nafis pardoz",
            shortDescription = "Tabiiy husningizga nafis jilo",
            favoriteTagline = "Tabiiy husningizga nafis jilo.",
            fullDescription = "Yengil va nafas oluvchi teksturalar yordamida yuz chiziqlarini nafis ta’kidlovchi kunduzgi yoki oqshomgi professional pardoz.",
            category = ServiceCategory.MAKEUP,
            durationMinutes = 60,
            priceUsd = 95,
            rating = 5.0,
            reviewCount = 76,
            imageResId = R.drawable.img_beauty_portrait_1795536624478,
            benefits = listOf(
                "Terini tayyorlovchi namlovchi baza",
                "Uzoq saqlanuvchi tabiiy ton",
                "Ko‘z va lablarga nozik urg‘u",
                "Kiprik va qoshlarni tartiblash"
            ),
            preparationNote = "Pardozdan oldin teringiz turiga mos namlantiruvchi baza qo‘llaniladi.",
            isFeaturedOnHome = false
        ),
        Service(
            id = "srv_brows",
            name = "Qosh shakli va kiprik qayrish",
            shortDescription = "Tabiiy husningizga nafis urg‘u",
            favoriteTagline = "Tabiiy husningizga nafis urg‘u.",
            fullDescription = "Yuzingiz nisbatiga mos qosh arxitekturasi, keratinli kiprik laminatsiyasi va tabiiy bo‘yash orqali nigohingizga ochiqlik va ifodalilik baxsh etish.",
            category = ServiceCategory.BROWS,
            durationMinutes = 50,
            priceUsd = 75,
            rating = 4.9,
            reviewCount = 89,
            imageResId = R.drawable.img_brow_lash,
            benefits = listOf(
                "Individual qosh shakli chizish",
                "Keratin bilan kipriklarni mustahkamlash",
                "6 haftagacha saqlanuvchi tabiiy qayrilish",
                "Allergiyasiz yumshoq tarkiblar"
            ),
            preparationNote = "Muolajadan keyin dastlabki 24 soat davomida suv tegizmaslik tavsiya etiladi.",
            isFeaturedOnHome = false
        )
    )

    val specialists: List<Specialist> = listOf(
        Specialist(
            id = "spec_sofia",
            name = "Sofia",
            roleTitle = "Yetakchi mutaxassis",
            shortDescription = "Yuz parvarishi, pardoz va qosh arxitekturasi bo‘yicha 7 yillik tajribaga ega yetakchi usta.",
            fullBio = "Sofia 7 yildan ortiq vaqtdan beri estetik kosmetologiya va tabiiy pardoz yo‘nalishida faoliyat yuritadi. U har bir mijozning teri holati va yuz xususiyatlaridan kelib chiqib, nozik va tabiiy natija yaratishga e’tibor qaratadi.",
            experienceYears = 7,
            rating = 4.9,
            imageResId = R.drawable.img_beauty_portrait_1795536624478,
            availableServiceIds = listOf("srv_facial", "srv_makeup", "srv_brows", "srv_hair")
        ),
        Specialist(
            id = "spec_maya",
            name = "Maya",
            roleTitle = "Go‘zallik ustasi",
            shortDescription = "Soch turmagi, tabiiy manikyur-pedikyur va parvarish bo‘yicha nozik didli mutaxassis.",
            fullBio = "Maya sochlarga tabiiy jilo berish, zamonaviy to‘lqinli turmaklar va sog‘lom tirnoq parvarishi bo‘yicha 5 yillik tajribaga ega. Uning ish uslubi — pokizalik, qulaylik va tabiiy go‘zallik.",
            experienceYears = 5,
            rating = 4.8,
            imageResId = R.drawable.img_maya_portrait,
            availableServiceIds = listOf("srv_hair", "srv_manicure", "srv_facial", "srv_brows")
        ),
        Specialist(
            id = "spec_any",
            name = "Istalgan",
            roleTitle = "Birinchi bo‘sh usta",
            shortDescription = "Tanlangan vaqtingizda bo‘sh bo‘lgan eng tajribali mutaxassis avtomatik biriktiriladi.",
            fullBio = "Siz uchun eng qulay vaqtda xizmat ko‘rsata oladigan bo‘sh mutaxassisimizni studiyamiz jamoasi tanlab beradi.",
            experienceYears = 5,
            rating = 4.9,
            imageResId = null,
            availableServiceIds = services.map { it.id },
            isAnyOption = true
        )
    )

    val bookingDays: List<BookingDayOption> = listOf(
        BookingDayOption("day_23", "Du", "23", "Dushanba, 23-noyabr 2026", "23-noyabr 2026", true),
        BookingDayOption("day_24", "Se", "24", "Seshanba, 24-noyabr 2026", "24-noyabr 2026", true),
        BookingDayOption("day_25", "Ch", "25", "Chorshanba, 25-noyabr 2026", "25-noyabr 2026", true),
        BookingDayOption("day_26", "Pa", "26", "Payshanba, 26-noyabr 2026", "26-noyabr 2026", true),
        BookingDayOption("day_27", "Ju", "27", "Juma, 27-noyabr 2026", "27-noyabr 2026", true),
        BookingDayOption("day_28", "Sh", "28", "Shanba, 28-noyabr 2026", "28-noyabr 2026", false),
        BookingDayOption("day_29", "Ya", "29", "Yakshanba, 29-noyabr 2026", "29-noyabr 2026", false)
    )

    val timeSlots: List<String> = listOf("10:00", "11:30", "13:00", "14:30")

    val initialFavoriteServiceIds: Set<String> = setOf(
        "srv_facial",
        "srv_manicure",
        "srv_hair"
    )

    val initialAppointments: List<Appointment> = listOf(
        Appointment(
            id = "apt_1",
            bookingCode = "BBS-2401",
            serviceId = "srv_facial",
            serviceName = "Yuzni nurlantirish",
            serviceImageResId = R.drawable.img_facial_glow_1795536605943,
            specialistId = "spec_sofia",
            specialistName = "Sofia",
            dateLabel = "Seshanba, 24-noyabr 2026",
            shortDateLabel = "24-noyabr 2026",
            timeSlot = "10:00",
            priceUsd = 85,
            status = AppointmentStatus.UPCOMING,
            customerName = defaultUser.fullName,
            customerPhone = defaultUser.phone,
            customerEmail = defaultUser.email
        ),
        Appointment(
            id = "apt_2",
            bookingCode = "BBS-2319",
            serviceId = "srv_manicure",
            serviceName = "Tabiiy manikyur-pedikyur",
            serviceImageResId = R.drawable.img_natural_manicure_1795536623595,
            specialistId = "spec_maya",
            specialistName = "Maya",
            dateLabel = "Seshanba, 03-noyabr 2026",
            shortDateLabel = "03-noyabr 2026",
            timeSlot = "14:30",
            priceUsd = 70,
            status = AppointmentStatus.COMPLETED,
            customerName = defaultUser.fullName,
            customerPhone = defaultUser.phone,
            customerEmail = defaultUser.email
        )
    )

    val galleryItems: List<GalleryItem> = listOf(
        GalleryItem(
            id = "gal_1",
            title = "Go‘zallik parvarishi",
            caption = "Iliq nur va mayin yuz massaji",
            imageResId = R.drawable.img_facial_glow_1795536605943
        ),
        GalleryItem(
            id = "gal_2",
            title = "Jilokor to‘lqinlar",
            caption = "Tabiiy hajm va ipakdek sochlar",
            imageResId = R.drawable.img_hair_waves_1795536605660
        ),
        GalleryItem(
            id = "gal_3",
            title = "Tabiiy manikyur",
            caption = "Har bir detalda nafislik",
            imageResId = R.drawable.img_natural_manicure_1795536623595
        ),
        GalleryItem(
            id = "gal_4",
            title = "Nafis pardoz",
            caption = "Yorqin va tabiiy ko‘rinish",
            imageResId = R.drawable.img_beauty_portrait_1795536624478
        ),
        GalleryItem(
            id = "gal_5",
            title = "Qosh va kiprik",
            caption = "Tabiiy urg‘u va shakl",
            imageResId = R.drawable.img_brow_lash
        )
    )

    val reviews: List<Review> = listOf(
        Review(
            id = "rev_1",
            serviceId = "srv_facial",
            authorName = "Madina K.",
            rating = 5.0,
            comment = "Sofia juda muloyim va mohir usta. Muolajadan so‘ng terim darhol yorqin va mayin bo‘lib qoldi.",
            dateLabel = "12-noyabr 2026"
        ),
        Review(
            id = "rev_2",
            serviceId = "srv_hair",
            authorName = "Laylo S.",
            rating = 4.9,
            comment = "Soch turmagi kechgacha juda chiroyli turdi, studiya muhiti esa juda sokin va yoqimli.",
            dateLabel = "09-noyabr 2026"
        ),
        Review(
            id = "rev_3",
            serviceId = "srv_manicure",
            authorName = "Amelia B.",
            rating = 5.0,
            comment = "Har safar Maya qo‘lida manikyur qildiraman. Juda ozoda va tabiiy natija!",
            dateLabel = "03-noyabr 2026"
        )
    )
}
