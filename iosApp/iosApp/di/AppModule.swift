//
//  AppModule.swift
//  iosApp
//
//  Created by Yeshwanth Munisifreddy on 06/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared

func startKoin() {
    _koin = InitKoinKt.doInitKoin().koin
}

 var _koin: Koin_coreKoin?


var koin: Koin_coreKoin{
    return _koin!
}

extension Koin_coreKoin{
    func get() -> GetDocsUseCase{
        return koin.getDependency(objCClass: GetDocsUseCase.self) as! GetDocsUseCase
    }
}
