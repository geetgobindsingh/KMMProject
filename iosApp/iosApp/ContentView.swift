import SwiftUI
import Combine
import Shared
import Kingfisher


enum ListItemContentType {
    case image
    case timer
}

class ImageModel: ObservableObject {
    let url: String
    
    init(url: String) {
        self.url = url
    }
}

class ListItemModel: ObservableObject, Identifiable {
    let id = UUID()
    let contentType: ListItemContentType
    let timer: SharedTimer?
    let image: ImageModel?

    init(contentType: ListItemContentType, timer: SharedTimer? = nil, image: ImageModel? = nil) {
        self.contentType = contentType
        self.timer = timer
        self.image = image
    }
}



class ListViewModel: ObservableObject {
    @Published var items: [ListItemModel] = []

    init() {
        loadData()
    }

    func loadData() {
        let commonItems = ItemsRepository().getItems()

        var tempItems: [ListItemModel] = []

        for item in commonItems {
            switch item.contentType {
                case .timer:
                    if let timerItem = item as? ListItem.TimerItem {
                        tempItems.append(ListItemModel(contentType: .timer, timer: timerItem.timer))
                    }
                case .image:
                    if let imageItem = item as? ListItem.ImageItem {
                        let imageModel = ImageModel(url: imageItem.image.url)
                        tempItems.append(ListItemModel(contentType: .image, image: imageModel))
                    }
                default:
                    break
                }
        }

        items = tempItems
    }
}



struct ContentView: View {
    @ObservedObject var viewModel = ListViewModel()

       var body: some View {
           List(viewModel.items) { item in
               switch item.contentType {
                case .timer:
                    if let timer = item.timer {
                        TimerItemView(timer: timer)
                            .padding()
                    }
                case .image:
                    if let imageUrl = item.image?.url {
                        ImageItemView(imageUrl: imageUrl)
                            .padding()
                    }
                }
           }
       }
   }


struct TimerItemView: View {
    @ObservedObject var timer: TimerWrapper

    init(timer: SharedTimer) {
            self.timer = TimerWrapper.getInstance(for: timer)
    }

    var body: some View {
        HStack {
                    Text("Timer \(timer.timer.id): \(timer.time)")

                    Button(action: {
                        
                    }) {
                        Text("Start")
                    }
                    .onTapGesture {
                        timer.start()
                    }
                    .padding()
                    .background(Color.green)
                    .foregroundColor(.white)
                    .cornerRadius(8)

                    Button(action: {
                        
                    }) {
                        Text("Stop")
                    }
                    .onTapGesture {
                        timer.stop()
                    }
                    .padding()
                    .background(Color.red)
                    .foregroundColor(.white)
                    .cornerRadius(8)
                }
                .padding()
    }
}

struct ImageItemView: View {
    private let imageUrl: String
        
        init(imageUrl: String) {
            self.imageUrl = imageUrl
        }
        
        var body: some View {
            KFImage(URL(string: imageUrl))
                .placeholder {
                    ProgressView()
                        .frame(maxWidth: .infinity, maxHeight: 50)
                }
                .retry(maxCount: 3, interval: .seconds(2))  // Retry mechanism
                .onFailure { error in
                    print("Error: \(error.localizedDescription)")
                }
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(maxWidth: .infinity, maxHeight: 50)
                .padding()
        }
    
}

class TimerWrapper: ObservableObject {
    let timer: SharedTimer
    @Published var time: Int = 0

    private init(timer: SharedTimer) {
        self.timer = timer
        self.subscribeToTimerUpdates()
    }
    
    private func subscribeToTimerUpdates() {
        print("Subscribing to updates for timer \(timer.id)")
             
             // Assuming `addValueUpdateHandler` registers a callback and returns a subscription/cancellation token
             timer.addValueUpdateHandler { [weak self] value in
                 print("Received update for \(self?.timer.id ?? -1): \(value)")
                 DispatchQueue.main.async {
                     self?.time = Int(truncating: value as NSNumber)
                 }
             }
    }
    
    // Use a shared instance dictionary to cache instances
    private static var cache = [Int: TimerWrapper]()

    static func getInstance(for timer: SharedTimer) -> TimerWrapper {
        let id = Int(timer.id)
        if let instance = cache[id] {
                return instance
            } else {
                let instance = TimerWrapper(timer: timer)
                cache[id] = instance
                return instance
            }
    }


    func start() {
        print("Start button clicked")
        timer.start()
    }

    func stop() {
        print("Stop button clicked")
        timer.stop()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
