import SwiftUI
import Shared

struct ContentView: View {
    @State private var showContent = false
    private var myValue = "Loading..."
    
    @ObservedObject private var viewModel  = MainViewModel()
    
    var body: some View {
        
        if viewModel.viewState == .loading {
            ProgressView()
        }
        let columns = [
              GridItem(.flexible()),
              GridItem(.flexible())
            ]
        GeometryReader { geometry in
            ScrollView {
                LazyVGrid(columns: columns, spacing: 3) {
                    ForEach(viewModel.photos, id: \.id) { item in
                        ImageView(url: URL(string:item.imageUrls?.thumb ?? "" )!,color: item.color)
                            .onAppear {
                                if item == viewModel.photos.last {
                                    viewModel.getData()
                                }
                            }
                    }
                }.padding([.leading,.trailing ],3)
            }
            .frame(width: geometry.size.width)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

