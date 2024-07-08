//
//  MainViewModel.swift
//  iosApp
//
//  Created by Yeshwanth Munisifreddy on 06/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared
import Combine

class MainViewModel: ObservableObject{
    @Published var photos: [Photo] = []
    @Published var viewState: ViewState = .loading
    private let getDocUsecase: GetDocsUseCase = koin.get()
    let limit : Int = 30
    var page: Int = 1
    enum ViewState: Equatable {
           case initial
           case loading
           case loaded
        case error(_ error: ErrorModel)
       }
    
    init(){
        getData()
    }

    func getData(){
        viewState = .loading
        getDocUsecase.getKtorDocs(
            onEach: { result in
                DispatchQueue.main.async {
                    switch onEnum(of: result) {
                    case .success(let value):
                        self.page+=1
                        if let newPhotos = value.data as? [Photo] {
                            self.photos += newPhotos
                            self.photos.removeDuplicates()
                        }
                        self.viewState = .loaded
                    case .failure(let errorResults):
                        self.viewState = .error(errorResults.error)
                    }
                }
            },
            onError: { error in
                self.viewState = .error(error ?? "something went wrong".toErrorDomain())
            },
            pageSize: 30,
            pageNUmber: Int32(page)
        )
    }
}

extension Array where Element: Hashable {
    func removingDuplicates() -> [Element] {
        var addedDict = [Element: Bool]()

        return filter {
            addedDict.updateValue(true, forKey: $0) == nil
        }
    }

    mutating func removeDuplicates() {
        self = self.removingDuplicates()
    }
}
